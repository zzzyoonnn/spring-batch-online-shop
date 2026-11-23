package com.batch.online_shop.dto;

import java.time.LocalDateTime;

/**
 * 상품 조회와 대량 상품 등록 결과에 사용하는 DTO입니다.
 *
 * <p>{@code packageQuantity}는 한 판매 단위에 들어 있는 구성품 수량이고,
 * {@code stockQuantity}는 현재 판매 가능한 상품 재고 수량입니다. 대량 업로드를 다시
 * 실행해도 같은 상품을 식별할 수 있도록 내부 ID와 별도로 상품 코드를 둡니다.</p>
 */
public record ProductDto(
        Long id,
        String productCode,
        String name,
        long packageQuantity,
        long price,
        long stockQuantity,
        String categoryCode,
        ProductStatus status,
        LocalDateTime updatedAt) {

    /** 재고 상태에 따라 상품의 판매 가능 여부를 표현합니다. */
    public enum ProductStatus {
        ON_SALE,
        SOLD_OUT,
        STOPPED
    }
}
