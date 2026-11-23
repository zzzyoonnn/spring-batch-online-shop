package com.batch.online_shop.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 일일 접속 통계 집계 결과를 전달하는 DTO입니다.
 */
public record DailyAccessStatDto(
        LocalDate statDate,
        long accessCount,
        long uniqueVisitorCount,
        long pageViewCount,
        LocalDateTime calculatedAt) {

    /** 기존의 단순 접속 건수 집계 결과도 표현할 수 있도록 제공합니다. */
    public DailyAccessStatDto(LocalDate statDate, Long accessCount) {
        this(statDate, accessCount == null ? 0 : accessCount, 0, 0, null);
    }
}
