package com.formacion.block17springbatch.config;

import com.formacion.block17springbatch.error.ErrorCounterComponent;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JobListener extends JobExecutionListenerSupport {
    @Autowired
    ErrorCounterComponent errorCounterComponent;
    private static final int MAX_ERROR_COUNT = 100;
    @Override
    public void beforeJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.STARTED){
            System.out.println("Job started");
        }
    }
    @Override
    public void afterJob(JobExecution jobExecution){
        if(errorCounterComponent.getErrorCounter().getCount() >= MAX_ERROR_COUNT) {
            jobExecution.setStatus(BatchStatus.FAILED);
            System.out.println("Job failed. Se han detectado 100 o más errores en los registros.");
        } else if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            System.out.println("Job ended");
        }
    }
}
