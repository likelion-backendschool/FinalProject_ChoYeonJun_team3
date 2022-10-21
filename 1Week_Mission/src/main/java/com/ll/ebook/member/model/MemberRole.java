package com.ll.ebook.member.model;

import lombok.Getter;

/**
 * 회원은 일반회원과 작가회원이 있다.
 * authLevel 의 값이 7이면 관리자의 역할도 수행할 수 있다.
 */
@Getter
public enum MemberRole {
    ADMIN(7),
    USER(1),
    AUTHOR(2);
    MemberRole(Integer authLevel) {
        this.authLevel = authLevel;
    }

    private Integer authLevel;
}