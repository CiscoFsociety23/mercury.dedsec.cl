package com.dedsec.mercury.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.dedsec.mercury.models.SimpleEmail;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String senderUser;

    private final Logger logger = LoggerFactory.getLogger(EmailService.class);

    public void sendSimpleMail(SimpleEmail emailData){
        try {
            logger.info("[ METHOD: sendSimpleMail() ]: Construyendo envio de correo a: " + emailData.getReciever());
            SimpleMailMessage email = new SimpleMailMessage();     
            email.setTo(emailData.getReciever());
            email.setFrom(senderUser);
            email.setSubject(emailData.getSubject());
            email.setText(emailData.getMessage());
            mailSender.send(email);
            logger.info("[ METHOD: sendSimpleMail() ]: Correo enviado con exito a: " + emailData.getReciever());
        } catch (Exception e) {
            logger.error("[ METHOD: sendSimpleMail() ]: Ha ocurrido un error en la contruccion/envio de correo");
            e.printStackTrace();
        }
    };

}
