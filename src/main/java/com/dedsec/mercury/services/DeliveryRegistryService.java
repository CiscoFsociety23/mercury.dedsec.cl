package com.dedsec.mercury.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.dedsec.mercury.dto.DeliveryRecord;
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

    public List<DeliveryRecord> getDeliveryRegistry(){
        logger.info("[ METHOD: getDeliveryRegistry() ]: Obteniendo registro de envios");
        List<DeliveryRegistry> registry = deliveryRegistryRepository.findAll();
        List<DeliveryRecord> record = new ArrayList<>();
        for (DeliveryRegistry deliveryRegistry : registry) {
            DeliveryRecord deliRecord = new DeliveryRecord();
            deliRecord.setId(deliveryRegistry.getIdDeliveryRegistry());
            deliRecord.setReciever(deliveryRegistry.getReciever());
            deliRecord.setSubject(deliveryRegistry.getSubject());
            deliRecord.setLayoutName(deliveryRegistry.getLayoutType().getLayoutName());
            deliRecord.setSendDate(deliveryRegistry.getSendTime());
            record.add(deliRecord);
        }
        return record;
    }

}
