package com.batch.online_shop.dto;

import java.time.LocalDateTime;

/**
 * 상품 대량 업로드 중 건너뛴 행과 실패 사유를 전달하는 DTO입니다.
 */
public record ProductImportErrorDto(
        Long id,
        Integer rowNumber,
        String productName,
        String errorMessage,
        String rawData,
        LocalDateTime createdAt) {
}
