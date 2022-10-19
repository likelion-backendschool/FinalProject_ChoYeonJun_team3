package com.ll.ebook.member.form;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberJoinForm {
    private String username;
    private String password;
    private String passwordConfirm;
    private String email;
    private String nickname;
}
