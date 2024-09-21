package com.example.marinepath.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;



@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendVerificationEmail(String to, String username, String verificationLink) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(to);
            helper.setSubject("Xác thực tài khoản của bạn");

            String content = "<html>" +
                    "<body>" +
                    "<div style=\"font-family: Arial, sans-serif; margin: 0; padding: 0;\">" +
                    "<div style=\"background-color: #007bff; padding: 20px; text-align: center; color: white;\">" +
                    "<h1>MarinePath</h1>" +
                    "</div>" +
                    "<div style=\"padding: 20px; background-color: #f9f9f9;\">" +
                    "<h2 style=\"color: #333;\">Hello " + username + ",</h2>" +
                    "<p style=\"font-size: 16px; color: #555;\">Thank you for registering an account at <strong>MarinePath</strong>. Please click the button below to verify your account:</p>" +
                    "<div style=\"text-align: center; margin: 20px;\">" +
                    "<a href=\"" + verificationLink + "\" style=\"background-color: #28a745; color: white; padding: 15px 30px; text-decoration: none; font-size: 16px; border-radius: 5px;\">Verify Account</a>" +
                    "</div>" +
                    "<p style=\"font-size: 16px; color: #555;\">If you did not request this account, please ignore this email.</p>" +
                    "</div>" +
                    "<div style=\"background-color: #007bff; padding: 10px; text-align: center; color: white;\">" +
                    "<p>&copy; 2024 MarinePath. All rights reserved.</p>" +
                    "</div>" +
                    "</div>" +
                    "</body>" +
                    "</html>";


            helper.setText(content, true);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }


    public void sendResetPasswordEmail(String toEmail, String name, String resetLink) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(toEmail);
            helper.setSubject("Reset your password");

            String content = "<html>" +
                    "<body>" +
                    "<div style=\"font-family: Arial, sans-serif; margin: 0; padding: 0;\">" +
                    "<div style=\"background-color: #007bff; padding: 20px; text-align: center; color: white;\">" +
                    "<h1>MarinePath</h1>" +
                    "</div>" +
                    "<div style=\"padding: 20px; background-color: #f9f9f9;\">" +
                    "<h2 style=\"color: #333;\">Hi " + name + ",</h2>" +
                    "<p style=\"font-size: 16px; color: #555;\">You requested to reset your password. Please click the button below to reset it:</p>" +
                    "<div style=\"text-align: center; margin: 20px;\">" +
                    "<a href=\"" + resetLink + "\" style=\"background-color: #dc3545; color: white; padding: 15px 30px; text-decoration: none; font-size: 16px; border-radius: 5px;\">Reset Password</a>" +
                    "</div>" +
                    "<p style=\"font-size: 16px; color: #555;\">If you did not request a password reset, please ignore this email.</p>" +
                    "</div>" +
                    "<div style=\"background-color: #007bff; padding: 10px; text-align: center; color: white;\">" +
                    "<p>&copy; 2024 MarinePath. All rights reserved.</p>" +
                    "</div>" +
                    "</div>" +
                    "</body>" +
                    "</html>";

            helper.setText(content, true);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }



}
