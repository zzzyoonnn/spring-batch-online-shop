package com.batch.online_shop.config;

import com.batch.online_shop.dto.ProductCsvRowDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.infrastructure.item.ExecutionContext;
import org.springframework.batch.infrastructure.item.file.FlatFileItemReader;

import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProductImportReaderConfigTest {

  @Test
  void readerIsStepScoped() throws NoSuchMethodException {
    Method readerMethod = ProductImportReaderConfig.class
            .getMethod("productCsvReader", String.class);

    assertTrue(readerMethod.isAnnotationPresent(StepScope.class));
  }

  @Test
  void readsCsvRowsIntoProductDto(@TempDir Path tempDir) throws Exception {
    Path csvFile = tempDir.resolve("products.csv");
    Files.writeString(csvFile, """
            name,price,categoryCode
            Product-000001,14700,ELEC
            Product-000002,28400,FASHION
            """);

    FlatFileItemReader<ProductCsvRowDto> reader =
            new ProductImportReaderConfig().productCsvReader(csvFile.toString());
    reader.open(new ExecutionContext());

    try {
      ProductCsvRowDto first = reader.read();
      ProductCsvRowDto second = reader.read();

      assertNotNull(first);
      assertEquals("Product-000001", first.getName());
      assertEquals(14700, first.getPrice());
      assertEquals("ELEC", first.getCategoryCode());

      assertNotNull(second);
      assertEquals("Product-000002", second.getName());
      assertEquals(28400, second.getPrice());
      assertEquals("FASHION", second.getCategoryCode());
      assertNull(reader.read());
    } finally {
      reader.close();
    }
  }
}
