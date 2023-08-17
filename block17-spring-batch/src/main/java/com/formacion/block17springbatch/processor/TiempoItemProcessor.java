package com.formacion.block17springbatch.processor;

import com.formacion.block17springbatch.infrastructure.dto.TiempoDto;
import com.formacion.block17springbatch.domain.Tiempo;
import org.springframework.batch.item.ItemProcessor;

public class TiempoItemProcessor implements ItemProcessor<Tiempo, Tiempo> {
    @Override
    public Tiempo process(Tiempo item) throws Exception {
        Tiempo tiempo = new Tiempo();
        tiempo.setLocalidad(item.getLocalidad());
        tiempo.setTemperatura(item.getTemperatura());
        tiempo.setFecha(item.getFecha());
        return tiempo;
    }
}
