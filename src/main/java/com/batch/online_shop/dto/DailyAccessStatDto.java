package com.batch.online_shop.dto;

import java.time.LocalDate;

/**
 * 일일 접속 통계 집계 결과를 전달하는 DTO입니다.
 */
public record DailyAccessStatDto(
        LocalDate statDate,
        Long accessCount) {
}
