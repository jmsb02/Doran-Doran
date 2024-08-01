package com.dorandoran.backend.session.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MailService {
    private final RedisService redisService;
    private final JavaMailSender mailSender = new JavaMailSenderImpl();

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Value("${props.reset-password-url}")
    private String resetPwUrl;

    public String makeUuid() {
        return UUID.randomUUID().toString();
    }

    public void sendFindIdEmail(String email) {
        String uuid = makeUuid();
        String title = "아이디 찾기 요청입니다.";
        String content = "아래의 링크를 클릭하면 아이디 찾기 페이지로 이동합니다."
                + "<a href=\"" + resetPwUrl + "/find-id?token=" + uuid + "\">"
                + resetPwUrl + "/find-id?token=" + uuid + "</a>";
        sendEmail(email, title, content);
        saveUuidAndEmail(uuid, email);
    }

    public String sendResetPasswordEmail(String email) {
        String uuid = makeUuid();
        String title = "요청하신 비밀번호 재설정 입니다."; // 이메일 제목
        String content = "아래의 링크를 클릭하면 비밀번호 재설정 페이지로 이동합니다."
                + "<a href=\"" + resetPwUrl + "/" + uuid + "\">"
                + resetPwUrl + "/" + uuid + "</a>";
        sendEmail(email, title, content);
        saveUuidAndEmail(uuid, email);
        return uuid;
    }

    /*
     * 이메일 전송 메서드
     */
    public void sendEmail(String toMail, String title, String content) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "utf-8");
            helper.setFrom(new InternetAddress(fromEmail));
            helper.setTo(toMail);
            helper.setSubject(title);
            helper.setText(content, true);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    /*
     * uuid와 email을 redis에 저장
     */
    @Transactional
    public void saveUuidAndEmail(String uuid, String email) {
        long uuidValidTime = 60 * 60 * 24 * 1000L; // 24시간
        redisService.setValuesWithTimeout(uuid, email, uuidValidTime);
    }
}