package com.batch.online_shop.dto;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 검색 엔진 인덱스에 반영할 문서와 동기화 방식을 전달하는 DTO입니다.
 */
public record SearchIndexSyncDto(
        String indexName,
        String documentId,
        EntityType entityType,
        Long entityId,
        Operation operation,
        Map<String, Object> document,
        LocalDateTime sourceUpdatedAt) {

    public SearchIndexSyncDto {
        document = document == null ? Map.of() : Map.copyOf(document);
    }

    /** 검색 문서의 원본 데이터 종류입니다. */
    public enum EntityType {
        PRODUCT,
        ORDER,
        USER
    }

    /** 검색 인덱스에 적용할 작업입니다. */
    public enum Operation {
        UPSERT,
        DELETE
    }
}
