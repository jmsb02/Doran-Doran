package com.dorandoran.backend.Member.domain;

import com.dorandoran.backend.Member.exception.MemberNotFoundException;
import com.dorandoran.backend.session.dto.JoinRequest;
import com.dorandoran.backend.session.dto.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Optional;
import java.util.regex.Pattern;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    // 닉네임 중복 체크
    public boolean checkNameDuplicate(String name) {
        return memberRepository.existsByName(name);
    }

    // 로그인Id 중복 체크
    public boolean checkLoginIdDuplicate(String loginId) {
        return memberRepository.existsByLoginId(loginId);
    }

    //이메일 중복 체크
    public boolean checkEmailDuplicate(String email) {
        return memberRepository.existsByEmail(email);
    }

    // 회원가입
    public void join(JoinRequest joinRequest, BindingResult bindingResult) {
        //닉네임 중복 확인
        if(checkNameDuplicate(joinRequest.getName())) {
            bindingResult.addError(new FieldError("joinRequest", "name", "닉네임이 중복됩니다."));
        }
        //로그인 아이디 중복 체크
        if(checkLoginIdDuplicate(joinRequest.getLoginId())) {
            bindingResult.addError(new FieldError("joinRequest", "loginId", "로그인 아이디가 중복됩니다."));
        }
        //이메일 중복 체크
        if(checkEmailDuplicate(joinRequest.getEmail())) {
            bindingResult.addError(new FieldError("joinRequest", "email", "존재하는 이메일입니다."));
        }
        //비밀번호 일치 체크
        if(joinRequest.getPassword().equals(joinRequest.getPasswordCheck())){
            bindingResult.addError(new FieldError("joinRequest", "passwordCheck", "비밀번호가 일치하지 않습니다."));
        }

        //비밀번호 형식 체크
        if(!Pattern.matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*])[A-Za-z\\d!@#$%^&*]{4,}$", joinRequest.getPassword())) {
            bindingResult.addError(new FieldError("joinRequest","password","비밀번호는 최소 4자 이상, 영문, 숫자 및 특수문자를 포함해야 합니다."));
        }
        if(bindingResult.hasErrors()) {
            return;
        }
        //비밀번호 해싱 후 저장
        String hashedPassword = BCrypt.hashpw(joinRequest.getPassword(), BCrypt.gensalt());
        memberRepository.save(joinRequest.toEntity(hashedPassword));
    }

    // 로그인
    public Member login(LoginRequest loginRequest) {
       Member member= memberRepository.findByLoginId(loginRequest.getLoginId())
               .orElseThrow(()->new MemberNotFoundException("아이디 또는 비밀번호가 잘못되었습니다."));
            if(BCrypt.checkpw(loginRequest.getPassword(), member.getPassword())) {
                throw new MemberNotFoundException("아이디 또는 비밀번호가 잘못되었습니다.");
            }
            return member;
    }

    // memberId를 입력받아 member를 리턴
    public Member getLoginMemberById(Long memberId) {
        return Optional.ofNullable(memberId)
                .flatMap(memberRepository::findById)
                .orElseThrow(() -> new MemberNotFoundException("Member not found with ID: " + memberId));
    }

    // loginId를 입력받아 member를 리턴
    public Member getLoginMemberByLoginId(String loginId) {
        return Optional.ofNullable(loginId)
                .flatMap(memberRepository::findByLoginId)
                .orElseThrow(() -> new MemberNotFoundException("Member not found with login ID: " + loginId));
    }
}