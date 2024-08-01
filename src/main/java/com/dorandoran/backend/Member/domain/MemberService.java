package com.dorandoran.backend.Member.domain;

import com.dorandoran.backend.Member.exception.InvalidUuidException;
import com.dorandoran.backend.Member.exception.MemberNotFoundException;
import com.dorandoran.backend.session.dto.JoinRequest;
import com.dorandoran.backend.session.dto.LoginRequest;
import com.dorandoran.backend.session.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.regex.Pattern;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final RedisService redisService;

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

    //이메일로 회원 체크
    public void checkMemberByEmail(String email) {
        if (!checkEmailDuplicate(email)) {
            throw new MemberNotFoundException("해당 이메일로 등록된 회원이 없습니다.");
        }
    }

    /*
    * 회원가입
    * */
    public void join(JoinRequest joinRequest, BindingResult bindingResult) {
        //닉네임 중복 확인
        if (checkNameDuplicate(joinRequest.getName())) {
            bindingResult.addError(new FieldError("joinRequest", "name", "닉네임이 중복됩니다."));
        }
        //로그인 아이디 중복 체크
        if (checkLoginIdDuplicate(joinRequest.getLoginId())) {
            bindingResult.addError(new FieldError("joinRequest", "loginId", "로그인 아이디가 중복됩니다."));
        }
        //이메일 중복 체크
        if (checkEmailDuplicate(joinRequest.getEmail())) {
            bindingResult.addError(new FieldError("joinRequest", "email", "존재하는 이메일입니다."));
        }
        //비밀번호 일치 체크
        if (!joinRequest.getPassword().equals(joinRequest.getPasswordCheck())) {
            bindingResult.addError(new FieldError("joinRequest", "passwordCheck", "비밀번호가 일치하지 않습니다."));
        }

        //비밀번호 형식 체크
        if (!Pattern.matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*])[A-Za-z\\d!@#$%^&*]{6,}$", joinRequest.getPassword())) {
            bindingResult.addError(new FieldError("joinRequest", "password", "비밀번호는 최소 6자 이상, 영문, 숫자 및 특수문자를 포함해야 합니다."));
        }
        if (bindingResult.hasErrors()) {
            return;
        }
        //비밀번호 해싱 후 저장
        String hashedPassword = BCrypt.hashpw(joinRequest.getPassword(), BCrypt.gensalt());
        memberRepository.save(joinRequest.toEntity(hashedPassword));
    }

    /*
    * 로그인
    * */
    public Member login(LoginRequest loginRequest) {
        Member member = memberRepository.findByLoginId(loginRequest.getLoginId())
                .orElseThrow(() -> new MemberNotFoundException("아이디 또는 비밀번호가 잘못되었습니다."));
        if (!BCrypt.checkpw(loginRequest.getPassword(), member.getPassword())) {
            throw new MemberNotFoundException("아이디 또는 비밀번호가 잘못되었습니다.");
        }
        return member;
    }

    // memberId를 입력받아 member를 리턴
    public Member getLoginMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("Member not found with ID: " + memberId));
    }

    // loginId를 입력받아 member를 리턴
    public Member getLoginMemberByLoginId(String loginId) {
        return memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new MemberNotFoundException("Member not found with login ID: " + loginId));
    }

    /*
     * 비밀번호 재설정
     */
    public void resetPassword(String uuid, String newPassword) {
        try {
            //redis에 uuid 있는지 확인, 없으면 error
            String email = redisService.getValues(uuid);
            if (email == null) {
                throw new InvalidUuidException();
            }

            //redis에서 uuid로 email을 찾아옴
            Member member = memberRepository.findByEmail(email)
                    .orElseThrow(() -> new MemberNotFoundException("Member not found with email: " + email));

            //비밀번호 재설정
            String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());
            member.updatePassword(hashedPassword);

            //비밀번호 업데이트 후 redis에서 uuid 삭제
            redisService.deleteValues(uuid);
        } catch (InvalidUuidException | MemberNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("비밀번호 재설정 중 오류가 발생했습니다.", e);
        }
    }
}