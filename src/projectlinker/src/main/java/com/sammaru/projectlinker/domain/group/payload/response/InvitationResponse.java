package com.sammaru.projectlinker.domain.group.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InvitationResponse {
    private Long invitationId;
    private String groupName;
    private String senderName;

}
