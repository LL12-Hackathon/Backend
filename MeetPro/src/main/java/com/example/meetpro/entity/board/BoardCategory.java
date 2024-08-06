package com.example.meetpro.entity.board;

import java.util.logging.Logger;

public enum BoardCategory {
    FREE, COUNSELING, JOB, QNA;

    public static BoardCategory of(String category) {
        Logger.getGlobal().info("카테고리 조회: " + category);

        if (category == null) {
            return null; // null 체크 추가
        }

        String normalizedCategory = category.trim().toLowerCase(); // 공백 제거 및 소문자 변환

        return switch (normalizedCategory) {
            case "{free}" -> BoardCategory.FREE;
            case "{counseling}" -> BoardCategory.COUNSELING;
            case "{job}" -> BoardCategory.JOB;
            case "{qna}" -> BoardCategory.QNA;
            default -> null;
        };
    }

}