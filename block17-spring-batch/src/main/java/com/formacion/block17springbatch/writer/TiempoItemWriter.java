package com.formacion.block17springbatch.writer;

import com.formacion.block17springbatch.domain.Tiempo;
import com.formacion.block17springbatch.error.ErrorCounterComponent;
import com.formacion.block17springbatch.repository.TiempoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger log = LoggerFactory.getLogger(TiempoItemWriter.class);
    private List<Tiempo> chunkErroneo = new ArrayList<>();
    private int erroresEnChunk = 0;

    public TiempoItemWriter(TiempoRepository tiempoRepository, ErrorCounterComponent errorCounterComponent) {
        this.tiempoRepository = tiempoRepository;
        this.errorCounterComponent = errorCounterComponent;
    }

    @Override
    public void write(List<? extends Tiempo> list) throws Exception {
        if (!isJobFailed) {
            log.info("_____ Registros recibidos de tiempo: {} _____", list.size());
            List<Tiempo> registrosErroneos = new ArrayList<>();

            list.forEach(tiempo -> {
                if (tiempo.getTemperatura() >= 50 || tiempo.getTemperatura() <= -20) {
                    log.info("** Error detectado ** Tiempo con temperatura inválida: {}", tiempo);
                    registrosErroneos.add(tiempo);
                    chunkErroneo.add(tiempo);
                    errorCounterComponent.getErrorCounter().increment();
                    erroresEnChunk++;

                } else {
                    log.info("Registro con temperatura válida, se guarda en la base de datos: {}", tiempo);
                    chunkErroneo.clear(); // Limpiar el chunkErroneo si el registro es válido
                    tiempoRepository.save(tiempo);
                    erroresEnChunk = 0; // Reiniciar el contador si el registro es válido
                }

                // Si se llega a 5 registros erróneos consecutivos en un chunk
                if (erroresEnChunk == 5) {
                    try {
                        log.info("*** Chunk erróneo detectado ***");
                        writeToErrorCSVChunk(chunkErroneo);
                        chunkErroneo.clear(); // Limpiar el chunkErroneo después de guardarlo
                        erroresEnChunk = 0; // Reiniciar el contador después de guardar el chunk
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });

            if (errorCounterComponent.getErrorCounter().getCount() >= 100) {
                log.info("*** Se han detectado 100 o más errores en los registros ***");
                tiempoRepository.deleteAll();
                isJobFailed = true;
            }

            if (!registrosErroneos.isEmpty()) {
                writeToCSV(registrosErroneos);
            }
        }
    }

    private void writeToCSV(List<Tiempo> registrosErroneos) throws IOException {

        log.info("Escribiendo registros erróneos en el archivo CSV");

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

    private void writeToErrorCSVChunk(List<Tiempo> registrosErroneos) throws IOException {
        log.info("Escribiendo chunk despreciado con 5 registros erróneos en el archivo CSV de chunks erróneos");
        String fileName = "CHUNKS_ERRONEOS.csv";
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));

        for (Tiempo tiempo : registrosErroneos) {
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
        }

        writer.close();
    }
}
