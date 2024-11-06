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
import com.dedsec.mercury.models.ValidacionEmail;
import com.dedsec.mercury.models.WelcomeEmail;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final PropertyService propertyService;
    private final LayoutService layoutService;

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

    public void sendWelcomeHtmlMail(WelcomeEmail emailData) {
        try {
            logger.info("[ METHOD: sendWelcomeHtmlMail() ]: Construyendo envio de correo HTML a: " + emailData.getReciever());
            String plantilla = layoutService.getLayout("Plantilla Bienvenida");
            byte[] platillaByte = Base64.getDecoder().decode(plantilla);
            String plantillaHtml = new String(platillaByte);
            logger.info("[ METHOD: sendWelcomeHtmlMail() ]: Obteniendo html de la propiedad");
            String plantillaHtmlOf = plantillaHtml.replace("{nombre}", emailData.getUserName());
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setTo(emailData.getReciever());
            helper.setFrom(senderUser);
            helper.setSubject(emailData.getSubject());
            helper.setText(plantillaHtmlOf, true);
            logger.info("[ METHOD: sendWelcomeHtmlMail() ]: Procesando envio...");
            mailSender.send(mimeMessage);
            logger.info("[ METHOD: sendWelcomeHtmlMail() ]: Correo HTML enviado con exito a: " + emailData.getReciever());
        } catch (Exception e) {
            logger.error("[ METHOD: sendWelcomeHtmlMail() ]: Ha ocurrido un error en la construccion/envio de correo HTML");
            e.printStackTrace();
        }
    };

    public void sendValidationHtmlMail(ValidacionEmail emailData) {
        try {
            logger.info("[ METHOD: sendValidationHtmlMail() ]: Construyendo envio de correo HTML a: " + emailData.getReciever());
            String plantilla = layoutService.getLayout("Plantilla Validacion");
            byte[] platillaByte = Base64.getDecoder().decode(plantilla);
            String plantillaHtml = new String(platillaByte);
            logger.info("[ METHOD: sendValidationHtmlMail() ]: Obteniendo html de la propiedad");
            String plantillaHtmlOf = plantillaHtml.replace("{URL_VALIDATION}", emailData.getValidation_url());
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setTo(emailData.getReciever());
            helper.setFrom(senderUser);
            helper.setSubject(emailData.getSubject());
            helper.setText(plantillaHtmlOf, true);
            logger.info("[ METHOD: sendValidationHtmlMail() ]: Procesando envio...");
            mailSender.send(mimeMessage);
            logger.info("[ METHOD: sendValidationHtmlMail() ]: Correo HTML enviado con exito a: " + emailData.getReciever());
        } catch (Exception e) {
            logger.error("[ METHOD: sendValidationHtmlMail() ]: Ha ocurrido un error en la construccion/envio de correo HTML");
            e.printStackTrace();
        }
    };

    public Boolean getRedinessValue(){
        try {
            logger.info("[ METHOD: getRedinessValue() ]: Obteniendo prueba rendiness");
            String rediness_val = propertyService.getProperty("Email Rediness");
            Boolean rediness = Boolean.parseBoolean(rediness_val);
            return rediness;
        } catch (Exception e) {
            logger.info("[ METHOD: getRedinessValue() ]: Ha ocurrido un error el obtener rediness");
            logger.error("[ METHOD: getRedinessValue() ]: " + e.getMessage());
            return false;
        }
    }

}
