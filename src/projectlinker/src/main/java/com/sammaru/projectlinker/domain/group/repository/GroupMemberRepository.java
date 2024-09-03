package com.sammaru.projectlinker.domain.group.repository;

import com.sammaru.projectlinker.domain.group.domain.Group;
import com.sammaru.projectlinker.domain.group.domain.GroupMember;
import com.sammaru.projectlinker.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupMemberRepository extends JpaRepository<GroupMember, Long> {

    List<GroupMember> findByGroup_GroupId(Long groupId);
    Optional<GroupMember> findByGroup_GroupIdAndUser_UserId(Long groupId, Long userId);



}

