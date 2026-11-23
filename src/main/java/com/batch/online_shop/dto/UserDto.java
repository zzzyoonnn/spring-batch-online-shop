package com.batch.online_shop.dto;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 회원 정보와 회원 관련 배치 처리에 필요한 상태를 전달하는 DTO입니다.
 *
 * <p>비밀번호는 평문이 아니라 반드시 단방향 암호화된 값만 담습니다. 총 포인트와 함께
 * 적립 건별 잔여 포인트를 보관해야 서로 다른 만료일을 기준으로 포인트를 만료시킬 수 있습니다.</p>
 */
public record UserDto(
        Long id,
        String passwordHash,
        String name,
        long pointBalance,
        AccountStatus accountStatus,
        LocalDateTime lastLoginAt,
        LocalDateTime dormantAt,
        List<PurchaseHistory> purchaseHistory,
        List<PointBalance> points) {

    public UserDto {
        purchaseHistory = purchaseHistory == null ? List.of() : List.copyOf(purchaseHistory);
        points = points == null ? List.of() : List.copyOf(points);
    }

    /** 회원 계정 상태입니다. */
    public enum AccountStatus {
        ACTIVE,
        DORMANT,
        WITHDRAWN
    }

    /**
     * 구매내역 한 건입니다. 상품 수량과 주문 당시 가격을 보존해 가격 변경 후에도
     * 과거 주문 금액을 정확히 조회할 수 있습니다.
     */
    public record PurchaseHistory(
            Long orderId,
            Long productId,
            String productName,
            long quantity,
            long unitPrice,
            long totalPrice,
            String orderStatus,
            LocalDateTime orderedAt) {
    }

    /**
     * 적립 건별 잔여 포인트입니다. 포인트 만료 배치는 {@code expiresAt}이 지난 건의
     * {@code remainingAmount}만 차감합니다.
     */
    public record PointBalance(
            Long pointId,
            long remainingAmount,
            LocalDateTime earnedAt,
            LocalDateTime expiresAt) {
    }
}
