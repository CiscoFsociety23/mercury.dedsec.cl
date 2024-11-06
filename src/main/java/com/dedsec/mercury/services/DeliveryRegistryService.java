package com.dedsec.mercury.services;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.dedsec.mercury.interfaces.DeliveryRegistryRepository;
import com.dedsec.mercury.models.DeliveryRegistry;
import com.dedsec.mercury.models.Layouts;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeliveryRegistryService {

    private final Logger logger = LoggerFactory.getLogger(DeliveryRegistryService.class);
    private final DeliveryRegistryRepository deliveryRegistryRepository;

    public void saveDeliveryRegistry(Layouts layoutType, String subject, String reciver){
        logger.info("[ METHOD: saveDeliveryRegistry() ]: Guardando registro de envio");
        DeliveryRegistry deliveryRegistry = new DeliveryRegistry();
        deliveryRegistry.setLayoutType(layoutType);
        deliveryRegistry.setSubject(subject);
        deliveryRegistry.setReciever(reciver);
        deliveryRegistry.setSendTime(new Date());
        deliveryRegistryRepository.save(deliveryRegistry);
        logger.info("[ METHOD: saveDeliveryRegistry() ]: Registro guardado");
    }

}
