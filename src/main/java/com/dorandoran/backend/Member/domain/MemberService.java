package com.dorandoran.backend.Member.domain;

import com.dorandoran.backend.Member.dto.req.LoginRequest;
import com.dorandoran.backend.Member.dto.req.MemberUpdateRequestDTO;
import com.dorandoran.backend.Member.dto.req.SignUpDTO;
import com.dorandoran.backend.Member.dto.res.MemberResponseDTO;
import com.dorandoran.backend.Member.exception.MemberNotFoundException;
import com.dorandoran.backend.Member.exception.DuplicateMemberException;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;
    private final HttpSession session;

    // 회원가입
    public Long signUp(SignUpDTO signUpDTO) {
        validateSignUpRequest(signUpDTO);

        String password = signUpDTO.getPassword();
        Member member = signUpDTO.toEntity();

        Member savedMember = memberRepository.save(member);
        log.info("회원가입 성공");
        return savedMember.getId();
    }

    // 로그인
    public void login(LoginRequest loginRequest) {
        log.info("Received login request: loginId={}, password={}", loginRequest.getLoginId(), loginRequest.getPassword());

        Member member = memberRepository.findByLoginId(loginRequest.getLoginId())
                .orElseThrow(() -> new MemberNotFoundException("잘못된 로그인 ID 또는 비밀번호입니다."));

        log.info("Member found with ID: {}, comparing passwords...", member.getId());

        if (!loginRequest.getPassword().equals(member.getPassword())) {
            log.warn("Password mismatch for user ID: {}", member.getId());
            throw new MemberNotFoundException("잘못된 로그인 ID 또는 비밀번호입니다.");
        }

        session.setAttribute("memberId", member.getId());
        log.info("Member with ID {} logged in.", member.getId());
    }

    // ID로 회원 찾기
    public Member findById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("ID " + memberId + "로 회원을 찾을 수 없습니다."));
    }

    // 로그인 ID로 회원 찾기
    public Member findByLoginId(String loginId) {
        return memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new MemberNotFoundException("로그인 ID " + loginId + "로 회원을 찾을 수 없습니다."));
    }

    // 회원 정보 조회
    public MemberResponseDTO getMemberInfo(Long memberId) {
        Member member = findById(memberId);
        return new MemberResponseDTO(member.getId(), member.getName(), member.getEmail(), member.getAddress());
    }

    // 회원 정보 업데이트
    public MemberResponseDTO updateMemberInfo(Long memberId, MemberUpdateRequestDTO updateRequestDTO) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("ID " + memberId + "로 회원을 찾을 수 없습니다."));

        if (updateRequestDTO.getPassword() != null && !updateRequestDTO.getPassword().isEmpty()) {
            member.updatePassword(updateRequestDTO.getPassword());
        }

        Member updatedMember = Member.builder()
                .id(member.getId())
                .loginId(member.getLoginId())
                .password(member.getPassword())
                .name(updateRequestDTO.getName() != null ? updateRequestDTO.getName() : member.getName())
                .email(updateRequestDTO.getEmail() != null ? updateRequestDTO.getEmail() : member.getEmail())
                .address(updateRequestDTO.getAddress() != null ? updateRequestDTO.getAddress() : member.getAddress())
                .build();

        memberRepository.save(updatedMember);
        return new MemberResponseDTO(updatedMember.getId(), updatedMember.getName(), updatedMember.getEmail(), updatedMember.getAddress());
    }

    // 회원 삭제
    public void deleteMember(Long memberId) {
        if (!memberRepository.existsById(memberId)) {
            throw new MemberNotFoundException("ID " + memberId + "로 회원을 찾을 수 없습니다.");
        }
        memberRepository.deleteById(memberId);
        log.info("Member with ID {} deleted.", memberId);
    }

    // 회원가입 요청 검증
    private void validateSignUpRequest(SignUpDTO signUpDTO) {
        if (memberRepository.existsByEmail(signUpDTO.getEmail())) {
            throw new DuplicateMemberException("이 이메일은 이미 사용 중입니다.");
        }

        if (memberRepository.existsByLoginId(signUpDTO.getLoginId())) {
            throw new DuplicateMemberException("이 아이디는 이미 사용 중입니다.");
        }

        if (memberRepository.existsByName(signUpDTO.getName())) {
            throw new DuplicateMemberException("이 닉네임은 이미 사용 중입니다.");
        }

        if (!isValidPassword(signUpDTO.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호 형식입니다.");
        }
    }

    // 비밀번호 형식 검증
    private boolean isValidPassword(String password) {
        return password.matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*])[A-Za-z\\d!@#$%^&*]{8,}$");
    }
}