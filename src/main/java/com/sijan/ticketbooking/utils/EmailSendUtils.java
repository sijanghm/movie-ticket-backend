package com.sijan.ticketbooking.utils;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class EmailSendUtils{

    @Value(("$(spring.mail.username)"))
    private String fromEmail = "noreply.dikshyatms@gmail.com";

    @Autowired
    private JavaMailSender javaMailSender;

    public String sendMail(List<MultipartFile> attachments, String to, String subject, String body) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(fromEmail);
            mimeMessageHelper.setTo(to);
           // mimeMessageHelper.setCc(cc);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(body, true);

            attachments.forEach(file -> {
                try {
                    mimeMessageHelper.addAttachment(file.getOriginalFilename(), new ByteArrayResource(file.getBytes()));
                } catch (MessagingException | IOException e) {
                    throw new RuntimeException(e);
                }
            });

            javaMailSender.send(mimeMessage);
            return "Mail Send success";
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }


}
