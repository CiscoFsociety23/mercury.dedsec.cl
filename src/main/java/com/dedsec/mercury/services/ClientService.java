package com.dedsec.mercury.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.dedsec.mercury.interfaces.ClientRepository;
import com.dedsec.mercury.models.Client;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    private static final Logger logger = LoggerFactory.getLogger(ClientService.class);

    public List<Client> getAllClients() {
        logger.info("[ Method: getAllClients() ]: Obteniendo listado de clientes");
        return clientRepository.findAll();
    };

    public Optional<Client> getClientById(Integer id) {
        logger.info("[ Method: getClientById() ]: Obteniendo cliente con id: " + id);
        Optional<Client> client = clientRepository.findById(id);
        logger.info("[ Method: getClientById() ]: Cliente obtenido, nombre: " + client.get().getName() + ", correo: " + client.get().getEmail());
        return client;
    };

    public Optional<Client> getClientByEmail(String email) {
        logger.info("[ Method: getClientByEmail() ]: Obteniendo cliente con correo: " + email);
        Optional<Client> client = clientRepository.findByEmail(email);
        logger.info("[ Method: getClientByEmail() ]: Cliente obtenido, nombre: " + client.get().getName() + ", correo: " + client.get().getEmail());
        return client;
    };

    public Client createClient(Client client) {
        logger.info("[ Method: createClient() ]: Creando el cliente: " + client.getName() + " Correo: " + client.getEmail());
        return clientRepository.save(client);
    };

    public void deleteClient(Integer id) {
        logger.info("[ Method: deleteClient() ]: Eliminando el cliente con id: " + id);
        clientRepository.deleteById(id);
    };

}
