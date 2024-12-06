package com.dedsec.mercury.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dedsec.mercury.dto.DeliveryRecord;
import com.dedsec.mercury.dto.SimpleEmail;
import com.dedsec.mercury.dto.ValidacionEmail;
import com.dedsec.mercury.dto.WelcomeEmail;
import com.dedsec.mercury.dto.RegistroAsistencia;
import com.dedsec.mercury.middlewares.EmailDedsecMiddleware;
import com.dedsec.mercury.services.DeliveryRegistryService;
import com.dedsec.mercury.services.EmailService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/EmailDedsec")
@RequiredArgsConstructor
public class EmailDedsecController {

    @Autowired
    private EmailService emailService;
    
    private final Logger logger = LoggerFactory.getLogger(EmailDedsecController.class);
    private final EmailDedsecMiddleware emailDedsecMiddleware;
    private final DeliveryRegistryService deliveryRegistryService;

    @GetMapping("/redinessEmail")
    public ResponseEntity<?> redinessEmail(){
        try {
            logger.info("[ GET /redinessEmail ]: Verificando estado del servicio");
            Boolean rediness = emailService.getRedinessValue();
            return new ResponseEntity<>(rediness, HttpStatus.OK);
        } catch (Exception e){
            logger.error("[ GET /redinessEmail ]: Ha ocurrido un error al obtener el estado del servicio");
            logger.error("[ GET /redinessEmail ]: " + e.getMessage());
            return new ResponseEntity<>(false, HttpStatus.OK);
        }
    }

    @PostMapping("/sendSimpleMail")
    public ResponseEntity<?> sendSimpleMail(@RequestBody SimpleEmail emailData, @RequestHeader("Authorization") String bearerToken) {
        try {
            logger.info("[ POST /sendSimpleMail ]: Obteniendo header de autorizacion");
            String token = bearerToken.split(" ")[1];
            if(emailDedsecMiddleware.verificarAdminProfile(token)){
                logger.info("[ POST /sendSimpleMail ]: Iniciando envio de correo simple, asunto: " + emailData.getSubject());
                emailService.sendSimpleMail(emailData);
                return new ResponseEntity<>(true, HttpStatus.OK);
            } else {
                logger.info("[ POST /sendSimpleMail ]: Perfil no valido para enviar correos");
                return new ResponseEntity<>(false, HttpStatus.OK);
            }
        } catch (Exception e) {
            logger.error("[ POST /sendSimpleMail ]: Ha ocurrido un error al procesar el envio");
            e.printStackTrace();
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    };

    @PostMapping("/sendWelcomeHtmlMail")
    public ResponseEntity<?> sendWelcomeHtmlMail(@RequestBody WelcomeEmail emailData, @RequestHeader("Authorization") String bearerToken){
        try {
            logger.info("[ POST /sendWelcomeHtmlMail ]: Obteniendo header de autorizacion");
            String token = bearerToken.split(" ")[1];
            if(emailDedsecMiddleware.verificarAdminProfile(token)){
                logger.info("[ POST /sendWelcomeHtmlMail ]: Iniciando envio de correo Html, asunto: " + emailData.getSubject());
                emailService.sendWelcomeHtmlMail(emailData);
                return new ResponseEntity<>(true, HttpStatus.OK);
            } else {
                logger.info("[ POST /sendWelcomeHtmlMail ]: Perfil no valido para enviar correos");
                return new ResponseEntity<>(false, HttpStatus.OK);
            }
        } catch (Exception e) {
            logger.error("[ POST /sendWelcomeHtmlMail ]: Ha ocurrido un error al procesar el envio");
            e.printStackTrace();
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    };

    @PostMapping("/sendValidationHtmlMail")
    public ResponseEntity<?> sendValidationHtmlMail(@RequestBody ValidacionEmail emailData, @RequestHeader("Authorization") String bearerToken){
        try {
            logger.info("[ POST /sendValidationHtmlMail ]: Obteniendo header de autorizacion");
            String token = bearerToken.split(" ")[1];
            if(emailDedsecMiddleware.verificarAdminProfile(token)){
                logger.info("[ POST /sendValidationHtmlMail ]: Iniciando envio de correo Html, asunto: " + emailData.getSubject());
                emailService.sendValidationHtmlMail(emailData);
                return new ResponseEntity<>(true, HttpStatus.OK);
            } else {
                logger.info("[ POST /sendValidationHtmlMail ]: Usuario no valido para enviar correos");
                return new ResponseEntity<>(false, HttpStatus.OK);
            }
        } catch (Exception e) {
            logger.error("[ POST /sendValidationHtmlMail ]: Ha ocurrido un error al procesar el envio");
            e.printStackTrace();
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    };

    @GetMapping("/deliveryRecord")
    public ResponseEntity<?> deliveryRecord(@RequestHeader("Authorization") String bearerToken){
        try {
            logger.info("[ GET /deliveryRecord ]: Obteniendo header de autorizacion");
            String token = bearerToken.split(" ")[1];
            if(emailDedsecMiddleware.verificarAdminProfile(token)){
                logger.info("[ GET /deliveryRecord ]: Procesando listado de envio de correos");
                List<DeliveryRecord> record= deliveryRegistryService.getDeliveryRegistry();
                return new ResponseEntity<>(record, HttpStatus.OK);
            } else {
                logger.error("[ GET /deliveryRecord ]: Usuario no autorizado para ver registros");
                return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            logger.error("[ GET /deliveryRecord ]: Ha ocurrido un error al procesar la solicitud. " + e.getMessage());
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("sendRegistroAsistencia")
    public ResponseEntity<?> sendRegistroAsistencia(@RequestBody RegistroAsistencia registro) {
        try {
            logger.info("[ POST /sendRegistroAsistencia ]: Procesando envio de registro de asistencia");
            emailService.sendRegistroAsistencia(registro);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("[ POST /sendRegistroAsistencia ]: Ha ocurrido un error al procesar la solicitud. " + e.getMessage());
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }
    

}
