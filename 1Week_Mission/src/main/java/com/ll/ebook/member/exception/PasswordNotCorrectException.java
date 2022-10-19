package com.ll.ebook.member.exception;

public class PasswordNotCorrectException extends RuntimeException{
    public PasswordNotCorrectException(String message) {
        super(message);
    }
}
