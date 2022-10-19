package com.ll.ebook.email;

import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailService {

    private JavaMailSender emailSender;

    public void sendWelcomeMessage(String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("멋북스에 오신걸 환영합니다!");
        message.setText("회원가입이 완료되었습니다!");
        emailSender.send(message);
    }
}