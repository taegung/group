package com.sammaru.projectlinker.domain.user.exception.exceptions;

public class InvalidAuthCodeException extends RuntimeException{
    public InvalidAuthCodeException(String message) {
        super(message);
    }
}
