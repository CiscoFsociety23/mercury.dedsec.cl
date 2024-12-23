package com.dedsec.mercury.services;

import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.dedsec.mercury.dto.RegistroAsistencia;
import com.dedsec.mercury.dto.SimpleEmail;
import com.dedsec.mercury.dto.ValidacionEmail;
import com.dedsec.mercury.dto.WelcomeEmail;
import com.dedsec.mercury.models.Layouts;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final PropertyService propertyService;
    private final LayoutService layoutService;
    private final DeliveryRegistryService deliveryRegistryService;

    @Value("${spring.mail.username}")
    private String senderUser;

    private final Logger logger = LoggerFactory.getLogger(EmailService.class);

    public void sendSimpleMail(SimpleEmail emailData){
        try {
            logger.info("[ METHOD: sendSimpleMail() ]: Construyendo envio de correo a: " + emailData.getReciever());
            Layouts layouts = layoutService.getLayout("Plantilla Mensaje Simple");
            String plantilla = layouts.getLayout();
            byte[] platillaByte = Base64.getDecoder().decode(plantilla);
            String plantillaHtml = new String(platillaByte);
            String plantillaHtmlFull = plantillaHtml.replace("{message}", emailData.getMessage());
            logger.info("[ METHOD: sendWelcomeHtmlMail() ]: Obteniendo html de la propiedad");
            MimeMessage mimeMesagge = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMesagge, true, "UTF-8");
            helper.setTo(emailData.getReciever());
            helper.setFrom(senderUser);
            helper.setSubject(emailData.getSubject());
            helper.setText(plantillaHtmlFull, true);
            mailSender.send(mimeMesagge);
            logger.info("[ METHOD: sendSimpleMail() ]: Correo enviado con exito a: " + emailData.getReciever());
            deliveryRegistryService.saveDeliveryRegistry(layouts, emailData.getSubject(), emailData.getReciever());
        } catch (Exception e) {
            logger.error("[ METHOD: sendSimpleMail() ]: Ha ocurrido un error en la contruccion/envio de correo");
            e.printStackTrace();
        }
    };

    public void sendWelcomeHtmlMail(WelcomeEmail emailData) {
        try {
            logger.info("[ METHOD: sendWelcomeHtmlMail() ]: Construyendo envio de correo HTML a: " + emailData.getReciever());
            Layouts layouts = layoutService.getLayout("Plantilla Bienvenida");
            String plantilla = layouts.getLayout();
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
            deliveryRegistryService.saveDeliveryRegistry(layouts, emailData.getSubject(), emailData.getReciever());
        } catch (Exception e) {
            logger.error("[ METHOD: sendWelcomeHtmlMail() ]: Ha ocurrido un error en la construccion/envio de correo HTML");
            e.printStackTrace();
        }
    };

    public void sendValidationHtmlMail(ValidacionEmail emailData) {
        try {
            logger.info("[ METHOD: sendValidationHtmlMail() ]: Construyendo envio de correo HTML a: " + emailData.getReciever());
            Layouts layouts = layoutService.getLayout("Plantilla Validacion");
            String plantilla = layouts.getLayout();
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
            deliveryRegistryService.saveDeliveryRegistry(layouts, emailData.getSubject(), emailData.getReciever());
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

    public void sendRegistroAsistencia(RegistroAsistencia registro){
        try {
            logger.info("[ METHOD: sendRegistroAsistencia() ]: Construyendo envio de correo HTML a: " + registro.getReciever());
            Layouts layouts = layoutService.getLayout("Registro Asistencia");
            String plantilla = layouts.getLayout();
            byte[] platillaByte = Base64.getDecoder().decode(plantilla);
            String plantillaHtml = new String(platillaByte);
            logger.info("[ METHOD: sendRegistroAsistencia() ]: Obteniendo html de la propiedad");
            String plantillaHtmlNombre = plantillaHtml.replace("{NOMBRE}", registro.getNombre());
            String plantillaHtmlRut = plantillaHtmlNombre.replace("{RUT}", registro.getRut());
            String plantillaHtmlTipoRegistro = plantillaHtmlRut.replace("{TIPO_REGISTRO}", registro.getTipoRegistro());
            String plantillaHtmlOf = plantillaHtmlTipoRegistro.replace("{FECHA_HORA}", registro.getFechaHora());
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setTo(registro.getReciever());
            helper.setFrom(senderUser);
            helper.setSubject(registro.getSubject());
            helper.setText(plantillaHtmlOf, true);
            logger.info("[ METHOD: sendRegistroAsistencia() ]: Procesando envio...");
            mailSender.send(mimeMessage);
            logger.info("[ METHOD: sendRegistroAsistencia() ]: Correo HTML enviado con exito a: " + registro.getReciever());
            deliveryRegistryService.saveDeliveryRegistry(layouts, registro.getSubject(), registro.getReciever());
        } catch (Exception e) {
            logger.error("[ METHOD: sendRegistroAsistencia() ]: Ha ocurrido un error en la construccion/envio de correo HTML");
            e.printStackTrace();
        }
    }

}
