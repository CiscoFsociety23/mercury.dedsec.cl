package com.dedsec.mercury.services;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.dedsec.mercury.interfaces.LayoutsRepository;
import com.dedsec.mercury.models.Layouts;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LayoutService {

    private final Logger logger = LoggerFactory.getLogger(LayoutService.class);
    private final LayoutsRepository layoutsRepository;

    public Layouts getLayout(String layoutName){
        try {
            logger.info("[ METHOD: getLayout() ]: Obteniendo Plantilla " + layoutName);
            Optional<Layouts> layout = layoutsRepository.findByLayoutName(layoutName);
            return layout.get();
        } catch (Exception e) {
            logger.error("[ METHOD: getLayout() ]: Ha ocurrido un error al obtener la plantilla");
            return null;
        }
    }

}
