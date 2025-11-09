package com.example.demo.service;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.stereotype.Service;

import java.util.Properties;

public class EmailService {
    private final String ADDRESS_FROM = "luanponick07@gmail.com";
    private final String PERSONAL_NAME = "teste";
    private final String USERNAME = "luanponick07@gmail.com";
    private String password;

    public void enviar(String subject, String messageText, String address) {
        password = "";
        try {
            Properties properties = new Properties();
            properties.put("mail.smtp.ssl.trust", "*");
            properties.put("mail.smtp.ssl.checkserveridentity", "true");
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.starttls", "true");
            properties.put("mail.smtp.host", "smtp.gmail.com");
            properties.put("mail.smtp.port", "465");
            properties.put("mail.smtp.socketFactory.port", "465");
            properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

            Session session = Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(USERNAME, password);
                }
            });

            session.setDebug(true);

            Address[] addresses = InternetAddress.parse(address);

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(ADDRESS_FROM, PERSONAL_NAME, "UTF-8"));
            message.setRecipients(Message.RecipientType.TO, addresses);
            message.setSubject(subject);
            message.setContent(messageText, "text/html; charset=utf-8");

            Transport.send(message);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

}
