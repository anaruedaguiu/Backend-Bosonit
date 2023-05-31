package com.formacion.block5logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    Logger logger = LoggerFactory.getLogger(Controller.class);

    @RequestMapping("/")
    public String index() {
        logger.warn("Mensaje a mivel de WARNING");
        logger.info("Mensaje a nivel de INFO");
        logger.debug("Mensaje a nivel de DEBUG");
        logger.trace("Mensaje a nivel de TRACE");
        logger.error("Mensaje a nivel de ERROR");

        return "Mira los logs";
    }
}