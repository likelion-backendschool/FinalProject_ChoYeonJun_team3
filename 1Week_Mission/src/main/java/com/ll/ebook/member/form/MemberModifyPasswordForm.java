package com.ll.ebook.member.form;

import lombok.Getter;

@Getter
public class MemberModifyPasswordForm {
    private String oldPassword;
    private String password;
    private String passwordConfirm;
}
