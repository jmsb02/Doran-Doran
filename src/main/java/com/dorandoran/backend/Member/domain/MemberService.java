package com.dorandoran.backend.Member.domain;

import com.dorandoran.backend.Member.dto.req.LoginRequest;
import com.dorandoran.backend.Member.dto.req.MemberUpdateRequestDTO;
import com.dorandoran.backend.Member.dto.req.SendResetPasswordReq;
import com.dorandoran.backend.Member.dto.req.SignUpRequest;
import com.dorandoran.backend.Member.dto.res.MemberResponseDTO;
import com.dorandoran.backend.Member.dto.res.SendResetPasswordRes;
import com.dorandoran.backend.Member.exception.DuplicateMemberException;
import com.dorandoran.backend.Member.exception.MemberNotFoundException;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;
    private final Mailservice mailservice;
    private final PasswordEncoder passwordEncoder;
    private final HttpSession session;

    public String determineHomePage(HttpSession session) {
        Long memberId = (Long) session.getAttribute("memberId");
        if (memberId == null) {
            throw new IllegalStateException("memberId를 찾을 수 없습니다. 로그인 해주세요.");
        }
        Member loginMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("ID가 " + memberId + "인 회원을 찾을 수 없습니다."));
        return "메인 페이지, 안녕하세요 " + loginMember.getName() + "님";
    }

    public void signUp(SignUpRequest signUpRequest) throws Exception {
        validateSignUpRequest(signUpRequest); // 이메일과 이름 중복 확인 및 비밀번호 일치 확인하는 메서드

        Member member = Member.builder()
                .name(signUpRequest.getName())
                .loginId(signUpRequest.getLoginId())
                .password(signUpRequest.getPassword())
                .email(signUpRequest.getEmail())
                .address(signUpRequest.getAddress())
                .build();
        memberRepository.save(member);
    }

    public void login(LoginRequest loginRequest, HttpSession session) {
        Member member = memberRepository.findByLoginId(loginRequest.getLoginId())
                .orElseThrow(() -> new MemberNotFoundException("존재하지 않는 회원입니다."));

        if(!member.getPassword().equals(loginRequest.getPassword())){
            throw new MemberNotFoundException();
        }


        // 로그인 성공 시 세션에 사용자 정보를 저장
        session.setAttribute("memberId", member.getId());
    }

    public String findLoginIdByEmail(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberNotFoundException("해당 이메일로 가입된 회원을 찾을 수 없습니다."));
        return "로그인 ID: " + member.getLoginId();
    }


    public SendResetPasswordRes sendResetPassword(SendResetPasswordReq resetPasswordEmailReq) {
        Member member = memberRepository.findByLoginIdAndEmail(
                        resetPasswordEmailReq.getLoginId(), resetPasswordEmailReq.getEmail())
                .orElseThrow(() -> new MemberNotFoundException("존재하지 않는 회원입니다."));

        String uuid = mailservice.sendResetPasswordEmail(member.getEmail());
        member.setResetToken(uuid);
        memberRepository.save(member);
        String resetPasswordLink = "https://frontend_domain.com/reset-password/" + uuid;

        return SendResetPasswordRes.builder()
                .resetPasswordLink(resetPasswordLink)
                .build();
    }

    public void resetPassword(String uuid, String newPassword) {
        Member member = memberRepository.findByResetToken(uuid)
                .orElseThrow(() -> new MemberNotFoundException("유효하지 않거나 만료된 토큰입니다."));

        member.setPassword(newPassword);
        memberRepository.save(member);
    }


    // 현재 사용자 정보 조회 메서드
    public ResponseEntity<MemberResponseDTO> getCurrentUserInfo(HttpSession session) {
        Long memberId = (Long) session.getAttribute("memberId");
        if (memberId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("사용자를 찾을 수 없습니다."));

        MemberResponseDTO memberResponseDTO = new MemberResponseDTO(member.getId(), member.getName(), member.getEmail(), member.getAddress());
        return ResponseEntity.ok(memberResponseDTO);
    }

    // 사용자 정보 업데이트 메서드
    public ResponseEntity<MemberResponseDTO> updateMemberInfo(MemberUpdateRequestDTO memberUpdateRequestDTO,Long memberId) {
      Member findMemeber = memberRepository.findById(memberId).orElseThrow();

        // 닉네임 중복 및 비밀번호 검증
        validateUpdateRequest(memberUpdateRequestDTO, findMemeber);

        // 회원 정보 업데이트
        findMemeber.update(
                memberUpdateRequestDTO.getName(),
                memberUpdateRequestDTO.getAddress(),
                memberUpdateRequestDTO.getEmail(),
                findMemeber.getPassword()  // 비밀번호 변경이 필요한 경우 따로 처리
        );

        memberRepository.save(findMemeber);

        MemberResponseDTO memberResponseDTO = new MemberResponseDTO(
                findMemeber.getId(),
                findMemeber.getName(),
                findMemeber.getEmail(),
                findMemeber.getAddress()
        );
        return ResponseEntity.ok(memberResponseDTO);
    }

    private void validateSignUpRequest(SignUpRequest signUpRequest) throws Exception {
        // 이메일 중복 확인
        if (memberRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new DuplicateMemberException("이메일이 이미 사용 중입니다.");
        }

        // 닉네임 중복 확인
        if (memberRepository.existsByName(signUpRequest.getName())) {
            throw new DuplicateMemberException("닉네임이 이미 사용 중입니다.");
        }

        // 비밀번호와 비밀번호 확인 일치 여부 확인
        if (!signUpRequest.getPassword().equals(signUpRequest.getPasswordCheck())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // 비밀번호 형식 확인
        if (!isValidPassword(signUpRequest.getPassword())) {
            throw new IllegalArgumentException("비밀번호 형식이 잘못되었습니다.");
        }
    }

    private void validateUpdateRequest(MemberUpdateRequestDTO memberUpdateRequestDTO, Member member) {
        // 닉네임 중복 확인
        if (!member.getName().equals(memberUpdateRequestDTO.getName()) && memberRepository.existsByName(memberUpdateRequestDTO.getName())) {
            throw new DuplicateMemberException("닉네임이 이미 사용 중입니다.");
        }

        // 비밀번호와 비밀번호 확인 일치 여부 확인
        if (memberUpdateRequestDTO.getPassword() != null && !memberUpdateRequestDTO.getPassword().isEmpty()) {
            if (!memberUpdateRequestDTO.getPassword().equals(memberUpdateRequestDTO.getPasswordCheck())) {
                throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
            }

            // 비밀번호 형식 확인
            if (!isValidPassword(memberUpdateRequestDTO.getPassword())) {
                throw new IllegalArgumentException("비밀번호 형식이 잘못되었습니다.");
            }
        }
    }

    private boolean isValidPassword(String password) {
        return password.matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*])[A-Za-z\\d!@#$%^&*]{8,}$");
    }

    public void deleteMember(Long memberId) {
        memberRepository.deleteById(memberId);
    }
}