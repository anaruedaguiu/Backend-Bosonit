package com.formacion.block17springbatch.processor;

import com.formacion.block17springbatch.domain.Resultado;
import org.springframework.batch.item.ItemProcessor;

public class ResultadoItemProcessor implements ItemProcessor<Resultado, Resultado> {

    @Override
    public Resultado process(Resultado resultado) throws Exception {
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
