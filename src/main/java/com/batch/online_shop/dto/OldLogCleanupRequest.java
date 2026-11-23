package com.batch.online_shop.dto;

import java.time.LocalDateTime;

/**
 * 오래된 로그 정리 배치의 삭제 범위를 전달하는 DTO입니다.
 * {@code cutoff} 이전의 로그가 정리 대상입니다.
 */
public record OldLogCleanupRequest(
        LocalDateTime cutoff,
        LogType logType,
        int batchSize) {

    private static final int DEFAULT_BATCH_SIZE = 1_000;

    public OldLogCleanupRequest {
        if (batchSize <= 0) {
            throw new IllegalArgumentException("batchSize는 0보다 커야 합니다");
        }
    }

    /** 기존 요청 형식은 모든 로그를 기본 청크 크기로 삭제합니다. */
    public OldLogCleanupRequest(LocalDateTime cutoff) {
        this(cutoff, LogType.ALL, DEFAULT_BATCH_SIZE);
    }

    /** 삭제할 로그 종류를 제한해 의도하지 않은 로그 삭제를 방지합니다. */
    public enum LogType {
        ALL,
        ACCESS,
        APPLICATION,
        AUDIT
    }
}
