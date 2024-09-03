package com.sammaru.projectlinker.domain.group.service;

import com.sammaru.projectlinker.domain.group.domain.Group;
import com.sammaru.projectlinker.domain.group.domain.GroupMember;
import com.sammaru.projectlinker.domain.group.domain.Invitation;
import com.sammaru.projectlinker.domain.group.payload.request.InviteMemberRequest;
import com.sammaru.projectlinker.domain.group.payload.request.UpdateMemberRequest;
import com.sammaru.projectlinker.domain.group.payload.response.InvitationResponse;
import com.sammaru.projectlinker.domain.group.payload.response.MemberInfoResponse;
import com.sammaru.projectlinker.domain.group.repository.GroupRepository;
import com.sammaru.projectlinker.domain.group.repository.GroupMemberRepository;
import com.sammaru.projectlinker.domain.group.repository.InvitationRepository;
import com.sammaru.projectlinker.domain.user.domain.User;
import com.sammaru.projectlinker.domain.user.repository.UserRepository;
import com.sammaru.projectlinker.domain.group.payload.request.CreateGroupRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class GroupService {
    private final GroupRepository groupRepository;
    private final GroupMemberRepository groupMemberRepository;
    private final UserRepository userRepository;
    private final InvitationRepository invitationRepository;

    public void createGroup(CreateGroupRequest request, Long userId) {
        User creator = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        groupRepository.save(
                Group.create(
                        request.name(),
                        creator
                )
        );
    }



    public void inviteMember(Long groupId, Long senderId, InviteMemberRequest request) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new NoSuchElementException("Group not found"));

        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new NoSuchElementException("Sender not found"));

        User receiver = userRepository.findById(request.receiverId())
                .orElseThrow(() -> new NoSuchElementException("Receiver not found"));

        Invitation invitation = Invitation.create(group, sender, receiver);
        invitationRepository.save(invitation);
    }

    public void acceptInvitationAndAddToGroup(Long invitationId, String status) {
        Invitation invitation = invitationRepository.findById(invitationId)
                .orElseThrow(() -> new NoSuchElementException("Invitation not found"));

        invitation.setStatus(status);
        invitationRepository.save(invitation);

        if ("ACCEPTED".equals(status)) {
            addMemberToGroup(invitation.getGroup().getGroupId(), invitation.getReceiver().getUserId());
        }
    }

    private void addMemberToGroup(Long groupId, Long userId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new NoSuchElementException("Group not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        GroupMember groupMember = GroupMember.builder()
                .group(group)
                .user(user)
                .isAdmin(false)
                .build();

        groupMemberRepository.save(groupMember);
    }


    public List<MemberInfoResponse> getGroupMemberIds(Long groupId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new NoSuchElementException("Group not found"));

        List<GroupMember> groupMembers = groupMemberRepository.findByGroup_GroupId(groupId);

        return groupMembers.stream()
                .map(groupMember -> new MemberInfoResponse(
                        groupMember.getUser().getUserId(),
                        groupMember.getUser().getName(),
                        groupMember.getUser().getPhnum()
                ))
                .collect(Collectors.toList());
    }


    public List<InvitationResponse> getReceivedInvitations(Long receiveId) {
        List<Invitation> invitations = invitationRepository.findByReceiver_UserId(receiveId);

        // PENDING 상태인 초대장만 필터링
        return invitations.stream()
                .filter(invitation -> "PENDING".equals(invitation.getStatus()))
                .map(invitation -> new InvitationResponse(
                        invitation.getInvitationId(),
                        invitation.getGroup().getName(),
                        invitation.getSender().getName()
                ))
                .collect(Collectors.toList());
    }
    @Scheduled(fixedRate = 5 * 60 * 1000) // 5분마다 실행
    public void deleteRejectedInvitation(Long invitationId) {
        Invitation invitation = invitationRepository.findById(invitationId)
                .orElseThrow(() -> new NoSuchElementException("Invitation not found"));

        if (("REJECTED".equals(invitation.getStatus())) &&("ACCEPTED".equals(invitation.getStatus()))) {
            invitationRepository.delete(invitation);
        } else {
            throw new IllegalArgumentException("Only rejected invitations can be deleted");
        }
    }

    public boolean isGroupAdmin(Long groupId, Long userId) {
        GroupMember groupMember = groupMemberRepository.findByGroup_GroupIdAndUser_UserId(groupId, userId)
                .orElseThrow(() -> new NoSuchElementException("Group member not found"));

        return groupMember.isAdmin();
    }

    public void removeGroupMember(Long groupId, Long userId, Long memberIdToRemove) {
        // 그룹장 확인
        if (!isGroupAdmin(groupId, userId)) {
            throw new SecurityException("Only group admins can remove members.");
        }

        // 그룹에서 제거할 멤버 확인
        GroupMember groupMemberToRemove = groupMemberRepository.findByGroup_GroupIdAndUser_UserId(groupId, memberIdToRemove)
                .orElseThrow(() -> new NoSuchElementException("Group member not found"));

        // 그룹에서 멤버 제거
        groupMemberRepository.delete(groupMemberToRemove);
    }




}
