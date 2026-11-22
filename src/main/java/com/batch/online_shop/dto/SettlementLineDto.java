package com.batch.online_shop.dto;

/**
 * 일일 매출 정산 과정에서 판매자별 정산 금액을 전달하는 DTO입니다.
 */
public record SettlementLineDto(
        Long sellerId,
        Long totalOrderAmount,
        Long commissionAmount,
        Long settlementAmount) {
}
