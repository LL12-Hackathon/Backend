package com.example.meetpro.domain.board;

public enum BoardCategory {
    FREE, COUNSELING, JOB, QNA;

    public static BoardCategory of(String category) {
        if (category.equalsIgnoreCase("free")) return BoardCategory.FREE;
        else if (category.equalsIgnoreCase("counseling")) return BoardCategory.COUNSELING;
        else if (category.equalsIgnoreCase("job")) return BoardCategory.JOB;
        else if (category.equalsIgnoreCase("qna")) return BoardCategory.QNA;
        else return null;
    }
}
