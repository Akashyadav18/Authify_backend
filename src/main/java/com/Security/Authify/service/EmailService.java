package com.Security.Authify.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSenderImpl mailSender;
    @Value("${spring.mail.properties.mail.smtp.from}")
    private String fromEmail;

    public EmailService(JavaMailSenderImpl mailSender) {
        this.mailSender = mailSender;
    }

    public void sendWelcomeEmail(String toEmail, String name) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(fromEmail);
        msg.setTo(toEmail);
        msg.setSubject("Welcome to Authify");
        msg.setText("Hello " + name + ",\n\nThanks for registering with us! \n\nRegards, \n welcome to Authify!");
        mailSender.send(msg);
    }

    public void sendResetOtpEmail(String toEmail, String otp){
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(fromEmail);
        msg.setTo(toEmail);
        msg.setSubject("Password Reset OTP");
        msg.setText("Your OTP is: " + otp + "\nUse this otp to reset your password\n\nThis OTP will expire in 10 minutes.");
        mailSender.send(msg);
    }

    public void sendOtpToVerifyEmail(String toEmail, String otp){
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(fromEmail);
        msg.setTo(toEmail);
        msg.setSubject("Email Verification OTP");
        msg.setText("Your OTP is: " + otp + "\nUse this otp to verify your email\n\nThis OTP will expire in 10 minutes.");
        mailSender.send(msg);
    }
}
