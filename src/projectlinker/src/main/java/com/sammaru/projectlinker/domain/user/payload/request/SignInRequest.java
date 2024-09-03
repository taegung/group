package com.sammaru.projectlinker.domain.user.payload.request;

public record SignInRequest(
    String email,
    String password
) {
}
