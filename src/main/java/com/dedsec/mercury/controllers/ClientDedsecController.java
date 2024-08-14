package com.dedsec.mercury.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dedsec.mercury.models.Client;
import com.dedsec.mercury.services.ClientService;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/ClientDedsec")
@RequiredArgsConstructor
public class ClientDedsecController {

    private final ClientService clientService;

    private static final Logger logger = LoggerFactory.getLogger(ClientDedsecController.class);
    
    @GetMapping("/getAllClients")
    public List<Client> getAllClients() {
        logger.info("[ GET /getAllClients ]: Procesando listado de clientes");
        return clientService.getAllClients();
    };

    @GetMapping("/getClientById")
    public Optional<Client> getClientById(@RequestParam Integer id) {
        logger.info("[ GET /getClientById ]: Procesando obtencion de cliente con id: " + id);
        return clientService.getClientById(id);
    };

    @GetMapping("/getClientByEmail")
    public Optional<Client> getClientByEmail(@RequestParam String email) {
        logger.info("[ GET /getClientByEmail ]: Procesando obtencion de cliente con email: " + email);
        return clientService.getClientByEmail(email);
    };

    @PostMapping("/createClient")
    public Client createClient(@RequestBody Client client) {
        logger.info("[ POST /createClient ]: Procesando la alta de cliente: " + client.getName());
        return clientService.createClient(client);
    };

    @DeleteMapping("/deleteClient")
    public ResponseEntity<Void> deleteClient(@RequestParam Integer id) {
        logger.info("[ DELETE /deleteClient ]: Procesando la baja de cliente con id: " + id);
        clientService.deleteClient(id);
        return ResponseEntity.noContent().build();
    };

}
