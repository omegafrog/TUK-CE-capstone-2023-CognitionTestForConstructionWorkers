package com.tukorea.cogTest.domain.enums;

public enum Role {
    ROLE_USER("일반 사용자"),
    ROLE_ADMIN("일반 관리자"),
    ROLE_SU_ADMIN("슈퍼 관리자");

    public final String description;

    Role(String description) {
        this.description = description;
    }
}
