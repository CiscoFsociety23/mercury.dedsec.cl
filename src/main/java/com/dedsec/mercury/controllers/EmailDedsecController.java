package com.dedsec.mercury.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dedsec.mercury.models.SimpleEmail;
import com.dedsec.mercury.models.ValidacionEmail;
import com.dedsec.mercury.models.WelcomeEmail;
import com.dedsec.mercury.services.EmailService;

@RestController
@RequestMapping("/EmailDedsec")
public class EmailDedsecController {

    @Autowired
    private EmailService emailService;
    
    private final Logger logger = LoggerFactory.getLogger(EmailDedsecController.class);

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
    public ResponseEntity<?> sendSimpleMail(@RequestBody SimpleEmail emailData) {
        try {
            logger.info("[ POST /sendSimpleMail ]: Iniciando envio de correo simple, asunto: " + emailData.getSubject());
            emailService.sendSimpleMail(emailData);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("[ POST /sendSimpleMail ]: Ha ocurrido un error al procesar el envio");
            e.printStackTrace();
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    };

    @PostMapping("/sendWelcomeHtmlMail")
    public ResponseEntity<?> sendWelcomeHtmlMail(@RequestBody WelcomeEmail emailData){
        try {
            logger.info("[ POST /sendWelcomeHtmlMail ]: Iniciando envio de correo Html, asunto: " + emailData.getSubject());
            emailService.sendWelcomeHtmlMail(emailData);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("[ POST /sendWelcomeHtmlMail ]: Ha ocurrido un error al procesar el envio");
            e.printStackTrace();
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    };

    @PostMapping("/sendValidationHtmlMail")
    public ResponseEntity<?> sendValidationHtmlMail(@RequestBody ValidacionEmail emailData){
        try {
            logger.info("[ POST /sendValidationHtmlMail ]: Iniciando envio de correo Html, asunto: " + emailData.getSubject());
            emailService.sendValidationHtmlMail(emailData);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("[ POST /sendValidationHtmlMail ]: Ha ocurrido un error al procesar el envio");
            e.printStackTrace();
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    };

}
