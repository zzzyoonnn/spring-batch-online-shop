package com.batch.online_shop.dto;

/**
 * 일일 매출 정산의 상품별 판매 내역을 전달하는 DTO입니다.
 *
 * <p>{@code unitPrice}는 주문 당시 상품 단가이고, {@code salesAmount}는 해당 상품의
 * 수량을 반영한 매출액입니다.</p>
 */
public record SettlementLineDto(
        Long productId,
        String productName,
        long quantity,
        long unitPrice,
        long salesAmount) {
}
