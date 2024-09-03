package com.sammaru.projectlinker.domain.user.payload.response;

import lombok.Builder;

@Builder
public record UserInfo(
        Long userId,
        String email,
        String name,
        String phnum,
        String university,
        String major,
        String grade,
        String universityStatus,
        String introduce,
        String experience
) {
}
