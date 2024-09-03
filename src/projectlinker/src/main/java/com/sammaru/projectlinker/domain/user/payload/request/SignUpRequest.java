package com.sammaru.projectlinker.domain.user.payload.request;

import java.util.List;

public record SignUpRequest(
        String email,
        String password,
        String name,
        String phnum,
        String university,
        String major,
        String grade,
        String universityStatus,
        String introduce,
        String experience,
        List<String> skills
) {
}
