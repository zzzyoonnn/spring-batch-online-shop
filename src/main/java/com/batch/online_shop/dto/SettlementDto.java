package com.batch.online_shop.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 하루 단위 매출 정산 결과를 전달하는 DTO입니다.
 *
 * <p>주문 건수와 판매 수량을 구분하고, 취소/환불 금액을 별도로 보관해 순매출의
 * 산출 근거를 확인할 수 있도록 구성합니다.</p>
 */
public record SettlementDto(
        LocalDate settlementDate,
        long dailyOrderCount,
        long totalProductQuantity,
        long grossSalesAmount,
        long refundAmount,
        long netSalesAmount,
        LocalDateTime calculatedAt,
        List<SettlementLineDto> products) {

    public SettlementDto {
        products = products == null ? List.of() : List.copyOf(products);
    }
}
