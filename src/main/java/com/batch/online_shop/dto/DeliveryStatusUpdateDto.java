package com.batch.online_shop.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

/**
 * 배송 상태 자동 변경 배치에서 갱신할 주문 정보를 전달하는 DTO입니다.
 */
public record DeliveryStatusUpdateDto(
        @NotNull Long orderId,
        @NotBlank String trackingNumber,
        @NotBlank String previousStatus,
        @NotBlank String nextStatus,
        @NotNull LocalDateTime changedAt) {
}
