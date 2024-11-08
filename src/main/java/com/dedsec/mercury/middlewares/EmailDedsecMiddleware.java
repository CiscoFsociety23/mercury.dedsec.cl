package com.dedsec.mercury.middlewares;

import java.net.URI;
import java.net.http.HttpClient;
import org.json.JSONObject;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.dedsec.mercury.services.PropertyService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EmailDedsecMiddleware {

    private final Logger logger = LoggerFactory.getLogger(EmailDedsecMiddleware.class);
    private final PropertyService propertyService;

    public Boolean verificarAdminProfile(String token){
        try {
            logger.info("[ METHOD: verificarAdminProfile() ]: Verificando perfil administrador");
            String urlJupiter = propertyService.getProperty("Jupiter Validacion URL");
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(urlJupiter))
                .header("Authorization", "Bearer " + token)
                .POST(HttpRequest.BodyPublishers.ofString(""))
                .build();
                logger.info("[ METHOD: verificarAdminProfile() ]: Realizando peticion a jupiter");
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if(response.statusCode() == 200){
                JSONObject jsonRes = new JSONObject(response.body());
                logger.info("[ METHOD: verificarAdminProfile() ]: Usuario Valido");
                return jsonRes.getBoolean("status");
            } else {
                logger.error("[ METHOD: verificarAdminProfile() ]: Ha ocurrido un error en el servicio Jupiter");
                return false;
            }
        } catch (Exception e) {
            logger.error("[ METHOD: verificarAdminProfile() ]: Ha ocurrido un error al verificar perfil. " + e.getMessage());
            return false;
        }
    }

}
