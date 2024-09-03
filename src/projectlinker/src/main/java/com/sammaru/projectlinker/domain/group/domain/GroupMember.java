package com.sammaru.projectlinker.domain.group.domain;

import com.sammaru.projectlinker.domain.user.domain.User;
import com.sammaru.projectlinker.global.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "group_members")
public class GroupMember extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_member_id")
    private Long groupMemberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "is_admin", nullable = false)
    private boolean isAdmin;

    @Column(name = "nickname")  // 새로운 칼럼 추가
    private String nickname;  // 기본값은 null로 설정됨

    // Setters and other methods
    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }
}
