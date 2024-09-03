package com.sammaru.projectlinker.domain.user.util;

import com.sammaru.projectlinker.domain.project.domain.Project;
import com.sammaru.projectlinker.domain.project.payload.response.ViewProjectListResponse;
import com.sammaru.projectlinker.domain.user.domain.User;
import com.sammaru.projectlinker.domain.user.payload.response.UserInfo;

public class UserInfoConverter {
    public static UserInfo from(User user){
        return UserInfo.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .name(user.getName())
                .phnum(user.getPhnum())
                .university(user.getUniversity())
                .major(user.getMajor())
                .grade(user.getGrade())
                .universityStatus(user.getUniversityStatus())
                .introduce(user.getIntroduce())
                .experience(user.getExperience())
                .build();
    }
}
