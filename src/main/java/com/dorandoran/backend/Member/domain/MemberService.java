package com.dorandoran.backend.Member.domain;

import com.dorandoran.backend.Member.dto.req.LoginRequest;
import com.dorandoran.backend.Member.dto.req.MemberUpdateRequestDTO;
import com.dorandoran.backend.Member.dto.req.SendResetPasswordReq;
import com.dorandoran.backend.Member.dto.req.SignUpRequest;
import com.dorandoran.backend.Member.dto.res.MemberResponseDTO;
import com.dorandoran.backend.Member.dto.res.SendResetPasswordRes;
import com.dorandoran.backend.Member.exception.MemberNotFoundException;
import com.dorandoran.backend.Member.exception.DuplicateMemberException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;
    private final Mailservice mailservice;
    private final HttpSession session;

//    public String determineHomePage(HttpServletRequest request) {
//        HttpSession session = request.getSession(false);
//        Long memberId = (Long) session.getAttribute("memberId");
//
//        Member loginMember = memberRepository.findById(memberId)
//                .orElseThrow(() -> new MemberNotFoundException("Member not found with ID: " + memberId));
//
//        return "Main Page, Hello " + loginMember.getName();
//    }

    public void signUp(SignUpRequest signUpRequest){
        validateSignUpRequest(signUpRequest); // 이메일과 이름 중복 확인 및 비밀번호 일치 확인

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(signUpRequest.getPassword());

        Member findMember = signUpRequest.toEntity(encodedPassword);

        System.out.println("member = " + findMember);
        memberRepository.save(findMember);
    }

    public void login(LoginRequest loginRequest) {
        Member member = memberRepository.findByLoginId(loginRequest.getLoginId())
                .orElseThrow(() -> new MemberNotFoundException("Invalid login ID or password"));

        if (loginRequest.getPassword().equals(member.getPassword())) {
            throw new MemberNotFoundException("Invalid login ID or password");
        }

        // 로그인 성공 시 세션에 사용자 정보를 저장
        session.setAttribute("memberId", member.getId());
    }

    public String findLoginIdByEmail(String token) {
        Member member = memberRepository.findByResetToken(token)
                .orElseThrow(() -> new MemberNotFoundException("Invalid or expired token"));
        return "Login ID: " + member.getLoginId();
    }

    public SendResetPasswordRes sendResetPassword(SendResetPasswordReq resetPasswordEmailReq) {
        Member member = memberRepository.findByLoginIdAndEmail(
                        resetPasswordEmailReq.getLoginId(), resetPasswordEmailReq.getEmail())
                .orElseThrow(() -> new MemberNotFoundException("No member found with this login ID and email"));

        String uuid = mailservice.sendResetPasswordEmail(member.getEmail());
        member.setResetToken(uuid);
        memberRepository.save(member);
        String resetPasswordLink = "https://yourdomain.com/reset-password/" + uuid;

        return SendResetPasswordRes.builder()
                .resetPasswordLink(resetPasswordLink)
                .build();
    }

    public void resetPassword(String uuid, String newPassword) {
        Member member = memberRepository.findByResetToken(uuid)
                .orElseThrow(() -> new MemberNotFoundException("Invalid or expired token"));

        member.setPassword(newPassword);
        log.info("member.getPassword" + member.getPassword());
        memberRepository.save(member);
    }

    public MemberResponseDTO getMemberInfo() {
        // 현재 인증된 사용자의 정보 가져옴.
        CustomUserDetails memberDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String loginId = memberDetails.getUsername();

        // 로그인 ID를 사용하여 회원 정보를 조회.
        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new RuntimeException("Member not found"));

        // DTO로 변환하여 반환.
        return new MemberResponseDTO(member.getId(), member.getName(), member.getEmail(), member.getAddress());
    }

    public MemberResponseDTO updateMemberInfo(MemberUpdateRequestDTO memberUpdateRequestDTO) {
        CustomUserDetails memberDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Member member = memberDetails.getMember();

        validateUpdateRequest(memberUpdateRequestDTO, member); // 닉네임 중복 및 비밀번호 검증

        // 회원 정보 업데이트
        member.update(
                memberUpdateRequestDTO.getName(),
                memberUpdateRequestDTO.getAddress(),
                memberUpdateRequestDTO.getEmail(),
                member.getPassword()
        );
        memberRepository.save(member);
        return new MemberResponseDTO(member.getId(), member.getName(), member.getEmail(), member.getAddress());
    }

    private void validateSignUpRequest(SignUpRequest signUpRequest) {
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
        // 닉네임 중복 확인 (자기 자신은 제외)
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