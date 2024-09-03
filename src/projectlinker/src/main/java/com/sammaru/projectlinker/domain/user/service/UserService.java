package com.sammaru.projectlinker.domain.user.service;

import com.sammaru.projectlinker.domain.user.domain.User;
import com.sammaru.projectlinker.domain.user.domain.UserSkill;
import com.sammaru.projectlinker.domain.user.exception.exceptions.DuplicateEmailException;
import com.sammaru.projectlinker.domain.user.payload.request.EditProfileRequest;
import com.sammaru.projectlinker.domain.user.payload.request.SignUpRequest;
import com.sammaru.projectlinker.domain.user.payload.response.UserInfo;
import com.sammaru.projectlinker.domain.user.repository.UserRepository;
import com.sammaru.projectlinker.domain.user.repository.UserSkillRepository;
import com.sammaru.projectlinker.domain.user.util.UserInfoConverter;
import com.sammaru.projectlinker.global.component.token.TokenResolver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserSkillRepository userSkillRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenResolver tokenResolver;

    public void checkEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new DuplicateEmailException("Email already exists");
        }
    }

    public void signUp(SignUpRequest request) {
        checkEmail(request.email());
        User user = User.signUp(
                request.email(),
                passwordEncoder.encode(request.password()),
                request.name(),
                request.phnum(),
                request.university(),
                request.major(),
                request.grade(),
                request.universityStatus(),
                request.introduce(),
                request.experience()
        );
        userRepository.save(user);

        List<String> skillNames = request.skills();
        for (String skillName : skillNames) {
            UserSkill userSkill = UserSkill.create(skillName);
            userSkillRepository.save(userSkill);
            user.getUserSkills().add(userSkill);
        }
    }

    public void editProfile(String token, EditProfileRequest request){
        User targetUser = userRepository.findByUserIdAndIsDeletedIsFalse(tokenResolver.getAccessClaims(token))
                .orElseThrow(() -> new NoSuchElementException("Email Not exists"));

        targetUser.editProfile(
                request.nickname(),
                request.phnum(),
                request.university(),
                request.major(),
                request.grade(),
                request.universityStatus(),
                request.introduce(),
                request.experience()
        );

        List<UserSkill> existingSkills = targetUser.getUserSkills();
        List<String> newSkillNames = request.skills();

        Set<String> existingSkillNames = existingSkills.stream()
                .map(UserSkill::getSkillName)
                .collect(Collectors.toSet());

        List<UserSkill> skillsToRemove = existingSkills.stream()
                .filter(skill -> !newSkillNames.contains(skill.getSkillName()))
                .collect(Collectors.toList());

        for (UserSkill skillToRemove : skillsToRemove) {
            targetUser.getUserSkills().remove(skillToRemove);
            userSkillRepository.delete(skillToRemove);
        }

        List<String> skillsToAdd = newSkillNames.stream()
                .filter(skillName -> !existingSkillNames.contains(skillName))
                .collect(Collectors.toList());

        for (String skillName : skillsToAdd) {
            UserSkill newUserSkill = UserSkill.create(skillName);
            userSkillRepository.save(newUserSkill);
            targetUser.getUserSkills().add(newUserSkill);
        }

        userRepository.save(targetUser);
    }

    public UserInfo viewUserProfile(String token){
        Long userId = tokenResolver.getAccessClaims(token);
        User targetUser = userRepository.findByUserIdAndIsDeletedIsFalse(userId)
                .orElseThrow(() -> new NoSuchElementException("Email Not exists"));
        return UserInfoConverter.from(targetUser);
    }

    public void resignUser(String token) {
        Long userId = tokenResolver.getAccessClaims(token);
        User targetUser = userRepository.findByUserIdAndIsDeletedIsFalse(userId)
                .orElseThrow(() -> new NoSuchElementException("Email Not exists"));
        targetUser.resignUser();
    }

    public UserInfo viewUserName(Long userId){
        User targetUser = userRepository.findByUserIdAndIsDeletedIsFalse(userId)
                .orElseThrow(() -> new NoSuchElementException("Email Not exists"));
        return UserInfoConverter.from(targetUser);
    }


}
