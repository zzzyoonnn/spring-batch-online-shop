package com.batch.online_shop.config;

import com.batch.online_shop.dto.ProductCsvRowDto;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.infrastructure.item.file.FlatFileItemReader;
import org.springframework.batch.infrastructure.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;

@Configuration
public class ProductImportReaderConfig {

  @Bean
  @StepScope
  public FlatFileItemReader<ProductCsvRowDto> productCsvReader(
          @Value("#{jobParameters['filePath']}") String filePath) {
    if (!StringUtils.hasText(filePath)) {
      throw new IllegalArgumentException("filePath JobParameter는 필수입니다");
    }

    return new FlatFileItemReaderBuilder<ProductCsvRowDto>()
            .name("productCsvReader")
            .resource(new FileSystemResource(filePath))
            .encoding(StandardCharsets.UTF_8.name())
            .strict(true)
            .linesToSkip(1)
            .delimited()
            .delimiter(",")
            .names("name", "price", "categoryCode")
            .targetType(ProductCsvRowDto.class)
            .build();
  }
}
