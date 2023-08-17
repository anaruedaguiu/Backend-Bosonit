package com.formacion.block17springbatch.writer;

import com.formacion.block17springbatch.domain.Tiempo;
import com.formacion.block17springbatch.domain.TiempoRiesgo;
import com.formacion.block17springbatch.repository.TiempoRepository;
import com.formacion.block17springbatch.repository.TiempoRiesgoRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class TiempoRiesgoItemWriter implements ItemWriter<TiempoRiesgo> {
    @Autowired
    TiempoRiesgoRepository tiempoRiesgoRepository;
    @Autowired
    TiempoRepository tiempoRepository;

    @Override
    public void write(List<? extends TiempoRiesgo> list) throws Exception {
        list.forEach(tiempoRiesgo -> {
            Tiempo tiempo = tiempoRepository
                    .findById(tiempoRiesgo.getTiempo().getIdTiempo())
                    .orElseThrow(() -> new RuntimeException("No existe el tiempo con id: " + tiempoRiesgo.getTiempo().getIdTiempo()));

            tiempo.setTiempoRiesgo(tiempoRiesgo);

            tiempoRepository.save(tiempo);
            tiempoRiesgoRepository.save(tiempoRiesgo);
        });
    }
}
