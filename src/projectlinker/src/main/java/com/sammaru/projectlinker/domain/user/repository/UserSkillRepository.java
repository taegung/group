package com.sammaru.projectlinker.domain.user.repository;

import com.sammaru.projectlinker.domain.user.domain.UserSkill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSkillRepository extends JpaRepository<UserSkill, Long> {
}
