package com.sammaru.projectlinker.domain.user.exception.exceptions;

public class FailSendEmailException extends RuntimeException{
    public FailSendEmailException(String message) {
        super(message);
    }
}
