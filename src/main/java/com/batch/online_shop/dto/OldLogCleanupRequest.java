package com.batch.online_shop.dto;

import java.time.LocalDateTime;

/**
 * 오래된 로그 정리 배치의 기준 시각을 전달하는 DTO입니다.
 * {@code cutoff} 이전의 로그가 정리 대상입니다.
 */
public record OldLogCleanupRequest(LocalDateTime cutoff) {
}
