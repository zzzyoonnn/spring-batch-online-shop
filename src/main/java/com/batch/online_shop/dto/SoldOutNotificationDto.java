package com.batch.online_shop.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

/**
 * 품절 상품 알림 배치가 생성하는 회원별 알림 대상 DTO입니다.
 */
public record SoldOutNotificationDto(
        @NotNull Long productId,
        @NotBlank String productName,
        @NotNull Long memberId,
        @NotBlank String channel,
        @NotBlank String status,
        @NotNull LocalDateTime createdAt) {
}
