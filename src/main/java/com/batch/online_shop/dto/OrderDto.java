package com.batch.online_shop.dto;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 주문자, 주문 상품 및 배송 상태를 전달하는 DTO입니다.
 *
 * <p>상품별 수량과 단가는 {@link OrderLine}에 보존하고, 주문 전체 상품 수와 결제 금액은
 * 상위 필드로 분리합니다. 이를 통해 여러 상품이 포함된 주문도 표현할 수 있습니다.</p>
 */
public record OrderDto(
        Long id,
        Long userId,
        String ordererName,
        long totalItemQuantity,
        long totalPrice,
        DeliveryStatus deliveryStatus,
        String trackingNumber,
        LocalDateTime orderedAt,
        LocalDateTime deliveryStatusChangedAt,
        List<OrderLine> products) {

    public OrderDto {
        products = products == null ? List.of() : List.copyOf(products);
    }

    /** 배송 자동 변경 배치가 사용하는 배송 상태입니다. */
    public enum DeliveryStatus {
        PAYMENT_COMPLETED,
        PREPARING,
        SHIPPED,
        IN_TRANSIT,
        DELIVERED,
        CANCELLED,
        RETURNED
    }

    /** 주문에 포함된 상품 한 종류의 수량과 주문 당시 가격입니다. */
    public record OrderLine(
            Long productId,
            String productName,
            long quantity,
            long unitPrice,
            long totalPrice) {
    }
}
