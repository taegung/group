package com.sammaru.projectlinker.domain.user.payload.request;

public record VerifyEmailRequest(
        String email,
        String authCode
) {
}
