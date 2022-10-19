package com.ll.ebook.member.exception;

public class PasswordAlreadyUseException extends RuntimeException{
    public PasswordAlreadyUseException(String message) {
        super(message);
    }
}
