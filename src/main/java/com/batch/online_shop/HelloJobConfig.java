package com.batch.online_shop;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class HelloJobConfig {

  private final JobRepository jobRepository;
  private final PlatformTransactionManager transactionManager;

  @Bean
  public Job helloJob() {
    return new JobBuilder("helloJob", jobRepository)
            .start(helloStep())
            .build();
  }

  @Bean
  public Step helloStep() {
    return new StepBuilder("helloStep", jobRepository)
            .tasklet((contribution, chunkContext) -> {
              System.out.println("Hello, Spring Batch!");
              return RepeatStatus.FINISHED;
            }, transactionManager)
            .build();
  }
}
