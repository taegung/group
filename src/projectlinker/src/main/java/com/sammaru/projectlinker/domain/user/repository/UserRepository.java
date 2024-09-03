package com.sammaru.projectlinker.domain.user.repository;


import com.sammaru.projectlinker.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    Optional<User> findByUserIdAndIsDeletedIsFalse(Long id);
    Optional<User> findByEmailAndIsDeletedIsFalse(String email);

    // 핸드폰 번호로 사용자 찾기taegung
    Optional<User> findByPhnum(String phoneNumber);

}
