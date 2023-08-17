package com.formacion.block17springbatch.processor;

import com.formacion.block17springbatch.domain.Tiempo;
import com.formacion.block17springbatch.domain.TiempoRiesgo;
import com.formacion.block17springbatch.infrastructure.dto.TiempoDto;
import org.springframework.batch.item.ItemProcessor;

public class TiempoRiesgoItemProcessor implements ItemProcessor<Tiempo, TiempoRiesgo> {
    @Override
    public TiempoRiesgo process(Tiempo tiempo) throws Exception {
        TiempoRiesgo tiempoRiesgo = new TiempoRiesgo();

        tiempoRiesgo.setIdTiempoRiesgo(tiempo.getIdTiempo());
        tiempoRiesgo.setTiempo(tiempo);
        tiempoRiesgo.setFechaPrediccion(tiempo.getFecha());

        if(tiempo.getTemperatura() > 36) {
            tiempoRiesgo.setRiesgo("ALTO");
        } else if (tiempo.getTemperatura() >= 32 && tiempo.getTemperatura() <= 36) {
            tiempoRiesgo.setRiesgo("MEDIO");
        } else if(tiempo.getTemperatura() < 32) {
            tiempoRiesgo.setRiesgo("BAJO");
        }

        return tiempoRiesgo;
    }
}
