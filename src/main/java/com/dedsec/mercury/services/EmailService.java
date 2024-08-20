package com.dedsec.mercury.services;

import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.dedsec.mercury.models.SimpleEmail;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final PropertyService propertyService;

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

    public void sendHtmlMail(SimpleEmail emailData, String layoutName) {
        try {
            logger.info("[ METHOD: sendHtmlMail() ]: Construyendo envio de correo HTML a: " + emailData.getReciever());
            String plantilla = propertyService.getProperty(layoutName);
            byte[] platillaByte = Base64.getDecoder().decode(plantilla);
            String plantillaHtml = new String(platillaByte);
            logger.info("[ METHOD: sendHtmlMail() ]: Obteniendo html de la propiedad");
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setTo(emailData.getReciever());
            helper.setFrom(senderUser);
            helper.setSubject(emailData.getSubject());
            helper.setText(plantillaHtml, true);
            logger.info("[ METHOD: sendHtmlMail() ]: Procesando envio...");
            mailSender.send(mimeMessage);
            logger.info("[ METHOD: sendHtmlMail() ]: Correo HTML enviado con exito a: " + emailData.getReciever());
        } catch (Exception e) {
            logger.error("[ METHOD: sendHtmlMail() ]: Ha ocurrido un error en la construccion/envio de correo HTML");
            e.printStackTrace();
        }
    };

}
