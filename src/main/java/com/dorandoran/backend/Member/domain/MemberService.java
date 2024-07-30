package com.dorandoran.backend.Member.domain;

import com.dorandoran.backend.Member.dto.JoinRequest;
import com.dorandoran.backend.Member.dto.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    // 로그인Id 중복 체크(회원가입 시 사용, 중복 시 true 리턴)
    public boolean checkLoginIdDuplicate(String loginId) {  // 수정된 부분
        return memberRepository.existsByLoginId(loginId);
    }

    // 닉네임 중복 체크(회원가입 시 사용, 중복 시 true 리턴)
    public boolean checkNameDuplicate(String name) {
        return memberRepository.existsByName(name);
    }

    // 회원가입 기능
    // 화면에서 joinRequest(loginId, password, name)을 입력받아 member로 변환 저장.
    // loginId와 name 중복 체크는 컨트롤러에서 진행->에러 메시지 출력 위해
    public void join(JoinRequest joinRequest) {
        String hashedPassword = BCrypt.hashpw(joinRequest.getPassword(), BCrypt.gensalt());
        memberRepository.save(joinRequest.toEntity(hashedPassword));
    }

    // 로그인 기능
    public Member login(LoginRequest loginRequest) {
        Optional<Member> optionalMember = memberRepository.findByLoginId(loginRequest.getLoginId());

        // 로그인 Id와 일치하는 사용자가 없다면 null 리턴
        if (optionalMember.isEmpty()) {
            return null;
        }
        Member member = optionalMember.get();

        // 찾아온 member의 password와 입력된 password가 다르면 null 리턴
        if (!BCrypt.checkpw(loginRequest.getPassword(), member.getPassword())) {
            return null;
        }
        return member;
    }

    // memberId를 입력받아 member를 리턴해주는 기능(인증, 인가 시 사용)
    public Member getLoginMemberById(Long memberId) {
        if (memberId == null) return null;
        Optional<Member> optionalMember = memberRepository.findById(memberId);
        if (optionalMember.isEmpty()) return null;

        return optionalMember.get();
    }

    // loginId를 입력받아 member를 리턴해주는 기능(인증, 인가 시 사용)
    public Member getLoginMemberByLoginId(String loginId) {
        if (loginId == null) return null;
        Optional<Member> optionalMember = memberRepository.findByLoginId(loginId);
        if (optionalMember.isEmpty()) return null;

        return optionalMember.get();
    }
}