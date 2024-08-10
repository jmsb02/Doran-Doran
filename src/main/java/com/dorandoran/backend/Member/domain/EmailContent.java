package com.dorandoran.backend.Member.domain;

public enum EmailContent {
    RESET_PASSWORD("Password Reset Request", "Click the link to reset your password: ");

    private final String subject;
    private final String body;

    EmailContent(String subject, String body) {
        this.subject = subject;
        this.body = body;
    }

    public String getSubject() {
        return subject;
    }

    public String getBody(String resetPasswordLink) {
        return body + resetPasswordLink;
    }
}
