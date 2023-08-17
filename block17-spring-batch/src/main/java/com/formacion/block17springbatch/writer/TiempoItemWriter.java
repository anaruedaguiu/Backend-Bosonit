package com.formacion.block17springbatch.writer;

import com.formacion.block17springbatch.domain.Tiempo;
import com.formacion.block17springbatch.error.ErrorCounterComponent;
import com.formacion.block17springbatch.repository.TiempoRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TiempoItemWriter implements ItemWriter<Tiempo> {

    @Autowired
    private TiempoRepository tiempoRepository;
    @Autowired
    private ErrorCounterComponent errorCounterComponent;

    private boolean isJobFailed = false;

    @Override
    public void write(List<? extends Tiempo> list) throws Exception {
        if (!isJobFailed) {
            List<Tiempo> registrosErroneos = new ArrayList<>();

            list.forEach(tiempo -> {
                if (tiempo.getTemperatura() >= 50 || tiempo.getTemperatura() <= -20) {
                    registrosErroneos.add(tiempo);
                    errorCounterComponent.getErrorCounter().increment();
                } else {
                    tiempoRepository.save(tiempo);
                }
            });

            if (errorCounterComponent.getErrorCounter().getCount() >= 100) {
                System.out.println("Se han detectado 100 o más errores en los registros");
                tiempoRepository.deleteAll();
                isJobFailed = true;
            }

            if (!registrosErroneos.isEmpty()) {
                writeToCSV(registrosErroneos);
            }
        }
    }

    private void writeToCSV(List<Tiempo> registrosErroneos) throws IOException {
        String fileName = "REGISTROS_ERRORES.csv";
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));

        registrosErroneos.forEach(tiempo -> {
            try {
                String line = String.format("%s,%s,%s",
                        tiempo.getLocalidad(),
                        tiempo.getFecha(),
                        tiempo.getTemperatura());
                writer.write(line);
                writer.newLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        writer.close();
    }
}
