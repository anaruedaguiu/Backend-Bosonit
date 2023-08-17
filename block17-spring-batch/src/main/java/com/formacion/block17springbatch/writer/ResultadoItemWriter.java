package com.formacion.block17springbatch.writer;

import com.formacion.block17springbatch.domain.Resultado;
import com.formacion.block17springbatch.repository.ResultadoRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ResultadoItemWriter implements ItemWriter<Resultado> {
    @Autowired
    ResultadoRepository resultadoRepository;

    @Override
    public void write(List<? extends Resultado> list) throws Exception {
        resultadoRepository.saveAll(list);
    }
}
