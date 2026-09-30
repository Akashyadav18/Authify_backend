package com.Security.Authify.service;

import com.Security.Authify.entity.ERole;
import com.Security.Authify.entity.UserEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSenderImpl mailSender;
    @Value("${spring.mail.properties.mail.smtp.from}")
    private String fromEmail;

    public EmailService(JavaMailSenderImpl mailSender) {
        this.mailSender = mailSender;
    }

    @Async
    public void sendWelcomeEmail(String toEmail, String name) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(fromEmail);
        msg.setTo(toEmail);
        msg.setSubject("Welcome to Authify");
        msg.setText("Hello " + name + ",\n\nThanks for registering with us! \n\nRegards, \n welcome to Authify!");
        mailSender.send(msg);
    }

    @Async
    public void sendResetOtpEmail(String toEmail, String otp){
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(fromEmail);
        msg.setTo(toEmail);
        msg.setSubject("Password Reset OTP");
        msg.setText("Your OTP is: " + otp + "\nUse this otp to reset your password\n\nThis OTP will expire in 10 minutes.");
        mailSender.send(msg);
    }

    @Async
    public void sendOtpToVerifyEmail(String toEmail, String otp){
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(fromEmail);
        msg.setTo(toEmail);
        msg.setSubject("Email Verification OTP");
        msg.setText("Your OTP is: " + otp + "\nUse this otp to verify your email\n\nThis OTP will expire in 10 minutes.");
        mailSender.send(msg);
    }

    @Async
    public void approvalAcceptedEmailMsg(String toEmail, String name){
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(fromEmail);
        msg.setTo(toEmail);
        msg.setSubject("Approval Accepted");
        msg.setText("Hello "+name+" Your account has been approved. \\n Now u can login. \\n\\nRegards, \\n welcome to Authify!\"");
        mailSender.send(msg);
    }

    @Async
    public void approvalRejectedEmailMsg(String toEmail, String name){
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(fromEmail);
        msg.setTo(toEmail);
        msg.setSubject("Approval Rejected");
        msg.setText("Hello "+name+" Your account has been rejected. \\n for more info contact Authify team \\n\\nRegards, \\n welcome to Authify!\"");
        mailSender.send(msg);
    }

    @Async
    public void invitationEmail(String email, String url, String admin, ERole role){
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(fromEmail);
        msg.setTo(email);
        msg.setSubject("Invitation to join Authify");
        msg.setText("Hello, \n\nYou have been invited to join Authify as a " + role + " by " + admin + ". \n\nPlease click the link below to accept the invitation: \n\n" + url +" \n\nRegards, \n welcome to Authify!");
        mailSender.send(msg);
    }
}
