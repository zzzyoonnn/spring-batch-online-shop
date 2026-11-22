package com.batch.online_shop.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 대량 상품 업로드 파일의 한 행을 표현하는 입력 DTO입니다.
 *
 * <p>Spring Batch의 {@code FlatFileItemReader}가 프로퍼티에 값을 바인딩할 수 있도록
 * 기본 생성자와 setter를 제공합니다.</p>
 */
@Getter
@Setter
@NoArgsConstructor
public class ProductCsvRowDto {

  @NotBlank(message = "상품명은 필수입니다")
  private String name;

  @NotNull(message = "가격은 필수입니다")
  @Min(value = 0, message = "가격은 0 이상이어야 합니다")
  private Integer price;

  @NotBlank(message = "카테고리는 필수입니다")
  private String categoryCode;
}
