package com.sammaru.projectlinker.domain.group.payload.request;

import lombok.Data;

@Data
public class UpdateMemberRequest {
    private Long groupId;
    private Long userId;
}
