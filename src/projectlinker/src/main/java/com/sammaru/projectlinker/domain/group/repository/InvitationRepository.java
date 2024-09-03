package com.sammaru.projectlinker.domain.group.repository;

import com.sammaru.projectlinker.domain.group.domain.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvitationRepository extends JpaRepository<Invitation, Long> {

    List<Invitation> findByReceiver_UserId(Long userId);
    List<Invitation> findByStatus(String status);
}
