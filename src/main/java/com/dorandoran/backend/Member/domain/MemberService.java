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
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // 회원가입
    public Long signUp(SignUpDTO signUpDTO) {
        validateSignUpRequest(signUpDTO); // 이메일과 이름 중복 확인 및 비밀번호 일치 확인

        String encodedPassword = passwordEncoder.encode(signUpDTO.getPassword());
        Member member = signUpDTO.toEntity(encodedPassword);

        log.info("Saving member: {}", member);
        Member savedMember = memberRepository.save(member);
        return savedMember.getId();
    }

    // 로그인
    public void login(LoginRequest loginRequest) {
        Member member = memberRepository.findByLoginId(loginRequest.getLoginId())
                .orElseThrow(() -> new MemberNotFoundException("Invalid login ID or password"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), member.getPassword())) {
            throw new MemberNotFoundException("Invalid login ID or password");
        }

        // 로그인 성공 시 세션에 사용자 정보를 저장
        session.setAttribute("memberId", member.getId());
        log.info("Member with ID {} logged in.", member.getId());
    }

    // ID로 회원 찾기
    public Member findById(Long userId) {
        return memberRepository.findById(userId)
                .orElseThrow(() -> new MemberNotFoundException("Member not found with ID: " + userId));
    }

    // 로그인 ID로 회원 찾기
    public Member findByLoginId(String loginId) {
        return memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new MemberNotFoundException("Member not found with login ID: " + loginId));
    }

    // 회원 정보 조회
    public MemberResponseDTO getMemberInfo(Long memberId) {
        Member member = findById(memberId);
        return new MemberResponseDTO(member.getId(), member.getName(), member.getEmail(), member.getAddress());
    }

    // 회원 정보 업데이트
    public MemberResponseDTO updateMemberInfo(Long memberId, MemberUpdateRequestDTO updateRequestDTO) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("Member not found with ID: "));

        // 비밀번호 업데이트가 필요한 경우
        if (updateRequestDTO.getPassword() != null && !updateRequestDTO.getPassword().isEmpty()) {
            member.updatePassword(passwordEncoder.encode(updateRequestDTO.getPassword()));
        }

        // 빌더 패턴을 사용하여 업데이트
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
            throw new MemberNotFoundException("Member not found with ID: " + memberId);
        }
        memberRepository.deleteById(memberId);
        log.info("Member with ID {} deleted.", memberId);
    }

    // 회원가입 요청 검증
    private void validateSignUpRequest(SignUpDTO signUpDTO) {
        if (memberRepository.existsByEmail(signUpDTO.getEmail())) {
            throw new DuplicateMemberException("The email is already in use.");
        }

        if (memberRepository.existsByName(signUpDTO.getName())) {
            throw new DuplicateMemberException("The nickname is already in use.");
        }

        if (!signUpDTO.getPassword().equals(signUpDTO.getPasswordCheck())) {
            throw new IllegalArgumentException("Passwords do not match.");
        }

        if (!isValidPassword(signUpDTO.getPassword())) {
            throw new IllegalArgumentException("Invalid password format.");
        }
    }


    // 비밀번호 형식 검증
    private boolean isValidPassword(String password) {
        return password.matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*])[A-Za-z\\d!@#$%^&*]{8,}$");
    }
}