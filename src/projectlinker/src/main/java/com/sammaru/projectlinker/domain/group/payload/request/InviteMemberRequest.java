package com.sammaru.projectlinker.domain.group.payload.request;

public record InviteMemberRequest(
        Long receiverId
        // 초대받을 유저의 ID
) {}
