package com.sammaru.projectlinker.domain.user.exception.exceptions;

public class DuplicateEmailException extends RuntimeException{
    public DuplicateEmailException(String email) {
        super("Email already exists: " + email);
    }
}
