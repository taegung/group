package com.sammaru.projectlinker.domain.user.domain;

import com.sammaru.projectlinker.global.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@DynamicUpdate
@Entity
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "email", nullable = false, length = 50)
    private String email;

    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @Column(name = "name", nullable = false, length = 15)
    private String name;

    @Column(name = "phnum", nullable = false, length = 20)
    private String phnum;

    @Column(name = "university", nullable = false)
    private String university;

    @Column(name = "major", nullable = false)
    private String major;

    @Column(name = "grade", nullable = false)
    private String grade;

    @Column(name = "university_status", nullable = false)
    private String universityStatus;

    @Column(name = "introduce")
    private String introduce;

    @Column(name = "experience")
    private String experience;

    @OneToMany
    @JoinColumn(name = "user_id")
    @Builder.Default
    private List<UserSkill> userSkills = new ArrayList<>();

    public static User signUp(String email, String password, String name, String phnum, String university, String major, String grade,
                              String universityStatus, String introduce, String experience) {
        return User.builder()
                .email(email)
                .password(password)
                .name(name)
                .phnum(phnum)
                .university(university)
                .major(major)
                .grade(grade)
                .universityStatus(universityStatus)
                .introduce(introduce)
                .experience(experience)
                .build();
    }

    public void editProfile(String name, String phnum, String university, String major, String grade,
                            String universityStatus, String introduce, String experience) {
        this.name = name;
        this.phnum = phnum;
        this.university = university;
        this.major = major;
        this.grade = grade;
        this.universityStatus = universityStatus;
        this.introduce = introduce;
        this.experience = experience;
    }

}
