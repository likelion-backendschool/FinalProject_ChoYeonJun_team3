package com.ll.ebook.member.exception;

public class PasswordConfirmNotMatchException extends RuntimeException{
    public PasswordConfirmNotMatchException(String message) {
        super(message);
    }
}
