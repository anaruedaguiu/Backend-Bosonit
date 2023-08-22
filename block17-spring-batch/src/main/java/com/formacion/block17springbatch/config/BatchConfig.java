package com.formacion.block17springbatch.config;

import com.formacion.block17springbatch.domain.Resultado;
import com.formacion.block17springbatch.error.ErrorCounterComponent;
import com.formacion.block17springbatch.domain.Tiempo;
import com.formacion.block17springbatch.domain.TiempoRiesgo;
import com.formacion.block17springbatch.mapper.TiempoRiesgoRowMapper;
import com.formacion.block17springbatch.mapper.TiempoRowMapper;
import com.formacion.block17springbatch.processor.ResultadoItemProcessor;
import com.formacion.block17springbatch.processor.TiempoItemProcessor;
import com.formacion.block17springbatch.processor.TiempoRiesgoItemProcessor;
import com.formacion.block17springbatch.repository.TiempoRepository;
import com.formacion.block17springbatch.writer.ResultadoItemWriter;
import com.formacion.block17springbatch.writer.TiempoItemWriter;
import com.formacion.block17springbatch.writer.TiempoRiesgoItemWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    DataSource dataSource;
    private static final Logger log = LoggerFactory.getLogger(BatchConfig.class);

    @Bean
    public FlatFileItemReader<Tiempo> tiempoReader() {
        log.info("--> Iniciando lectura de registros desde el archivo prueba.csv");
        return new FlatFileItemReaderBuilder<Tiempo>()
                .name("tiempoReader")
                .resource(new ClassPathResource("prueba.csv"))
                .linesToSkip(1)
                .delimited()
                .names("localidad", "temperatura", "fecha")
                .fieldSetMapper(new BeanWrapperFieldSetMapper<Tiempo>() {{
                    setTargetType(Tiempo.class);
                }})
                .build();
    }

    @Bean
    public JdbcCursorItemReader<Tiempo> tiempoReaderDataBase(){
        log.info("--> Lectura de objetos Tiempo de la base de datos");
        JdbcCursorItemReader<Tiempo> reader = new JdbcCursorItemReader<>();
        reader.setSql("SELECT * FROM tiempo");
        reader.setDataSource(dataSource);
        reader.setFetchSize(100);
        reader.setRowMapper(new TiempoRowMapper());

        return reader;
    }

    @Bean
    public JdbcCursorItemReader<TiempoRiesgo> tiempoRiesgoItemReader() {
        log.info("--> Lectura de objetos Tiempo-Riesgo de la base de datos");
        JdbcCursorItemReader<TiempoRiesgo> reader = new JdbcCursorItemReader<>();
        reader.setSql("SELECT * FROM tiempo_riesgo");
        reader.setDataSource(dataSource);
        reader.setFetchSize(100);
        reader.setRowMapper(new TiempoRiesgoRowMapper());

        return reader;
    }

    @Bean
    public JdbcCursorItemReader<Resultado> resultadoItemReader() {
        log.info("--> Lectura de objetos Resultado de la base de datos. Cálculo de la tª media y nº de mediciones");
        JdbcCursorItemReader<Resultado> cursorItemReader = new JdbcCursorItemReader<>();
        cursorItemReader.setDataSource(dataSource);
        cursorItemReader.setSql(
                "SELECT "
                        + "t.localidad AS localidad, "
                        + "MONTH(tr.fecha_prediccion) AS mes, "
                        + "YEAR(tr.fecha_prediccion) AS anio, "
                        + "COUNT(*) AS numero_mediciones, "
                        + "AVG(t.temperatura) AS temperatura_media, "
                        + "FROM tiempo_riesgo tr, tiempo t "
                        + "WHERE tr.id_Tiempo_Riesgo = t.id_Tiempo "
                        + "GROUP BY localidad, mes, anio "
                        + "ORDER BY localidad ASC, mes ASC"
        );

        cursorItemReader.setRowMapper(new BeanPropertyRowMapper<>(Resultado.class));

        return cursorItemReader;
    }


    //Processors
    @Bean
    public ItemProcessor<Tiempo, Tiempo> tiempoItemProcessor() {
        return new TiempoItemProcessor();
    }

    @Bean
    public ItemProcessor<Tiempo, TiempoRiesgo> tiempoRiesgoItemProcessor(){
        return new TiempoRiesgoItemProcessor();
    }

    @Bean
    public ItemProcessor<Resultado, Resultado> resultadoItemProcessor(){
        return new ResultadoItemProcessor();
    }

    //Writers
    @Bean
    public ItemWriter<Tiempo> tiempoItemWriter(ErrorCounterComponent errorCounterComponent, TiempoRepository tiempoRepository) {
        return new TiempoItemWriter(tiempoRepository, errorCounterComponent);
    }

    @Bean
    public ItemWriter<TiempoRiesgo> tiempoRiesgoItemWriter() { return new TiempoRiesgoItemWriter(); }

    @Bean
    public ItemWriter<Resultado> resultadoItemWriter() {
        return new ResultadoItemWriter();
    }

    //Listener
    @Bean
    public JobExecutionListener listener() {
        return new JobListener();
    }


    //Step
    @Bean
    public Step stepTiempoCsv(ErrorCounterComponent errorCounterComponent, TiempoRepository tiempoRepository) {
        return stepBuilderFactory.get("step1")
                .<Tiempo, Tiempo>chunk(5)
                .reader(tiempoReader())
                .processor(tiempoItemProcessor())
                .writer(new TiempoItemWriter(tiempoRepository, errorCounterComponent))
                .listener(listener())
                .build();
    }

    @Bean
    public Step stepRiesgoTiempo() {
        return stepBuilderFactory.get("step2")
                .<Tiempo, TiempoRiesgo>chunk(5)
                .reader(tiempoReaderDataBase())
                .processor(tiempoRiesgoItemProcessor())
                .writer(tiempoRiesgoItemWriter())
                .build();
    }

    @Bean
    public Step stepResultados() {
        return stepBuilderFactory.get("step3")
                .<Resultado, Resultado>chunk(5)
                .reader(resultadoItemReader())
                .processor(resultadoItemProcessor())
                .writer(resultadoItemWriter())
                .build();
    }

    @Bean
    public Job jobCsvTiempo(ErrorCounterComponent errorCounterComponent, TiempoRepository tiempoRepository) {
        System.out.println("- Este es el JOB -");
        return jobBuilderFactory.get("jobCsvTiempo")
                .incrementer(new RunIdIncrementer())
                .listener(listener())
                .flow(stepTiempoCsv(errorCounterComponent, tiempoRepository))
                .next(stepRiesgoTiempo())
                .next(stepResultados())
                .end()
                .build();
    }
}
