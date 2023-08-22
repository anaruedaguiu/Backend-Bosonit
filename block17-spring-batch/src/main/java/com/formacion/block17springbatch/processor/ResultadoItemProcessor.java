package com.formacion.block17springbatch.processor;

import com.formacion.block17springbatch.domain.Resultado;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class ResultadoItemProcessor implements ItemProcessor<Resultado, Resultado> {
    private static final Logger log = LoggerFactory.getLogger(ResultadoItemProcessor.class);

    @Override
    public Resultado process(Resultado resultado) throws Exception {
        log.info("Calculando el riesgo según la temperatura media");
        if(resultado.getTemperaturaMedia() > 36) {
            resultado.setRiesgo("ALTO");
        } else if (resultado.getTemperaturaMedia() >= 32 && resultado.getTemperaturaMedia() <= 36) {
            resultado.setRiesgo("MEDIO");
        } else if (resultado.getTemperaturaMedia() < 32) {
            resultado.setRiesgo("BAJO");
        }
        return resultado;
    }
}
