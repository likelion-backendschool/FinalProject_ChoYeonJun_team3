package com.ll.ebook.member.model;

import lombok.Getter;

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