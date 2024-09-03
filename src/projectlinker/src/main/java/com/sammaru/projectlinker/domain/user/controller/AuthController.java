package com.sammaru.projectlinker.domain.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;

import com.sammaru.projectlinker.domain.user.payload.request.SignInRequest;
import com.sammaru.projectlinker.domain.user.service.AuthService;
import com.sammaru.projectlinker.global.component.token.ReturnToken;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/api/v1")
@RequiredArgsConstructor
@Validated
public class AuthController {
    private final AuthService authService;

    @Tag(name = "로그인", description = "로그인 api")
    @PostMapping("/signin")
    public ResponseEntity<ReturnToken> signIn(@RequestBody @Valid SignInRequest request) {
        return ResponseEntity.ok(authService.signIn(request));
    }

    @Tag(name = "로그아웃", description = "로그아웃 api, AccessToken, RefreshToken 필요")
    @PostMapping("/signout")
    public ResponseEntity<Void> signOut(HttpServletRequest request){
        authService.signOut(request);
        return ResponseEntity.ok().build();
    }

    @Tag(name = "토큰 재발급", description = "토큰 만료시 재발급 하는 api, RefreshToken 필요")
    @PostMapping("/refresh")
    public ResponseEntity<ReturnToken> refresh(HttpServletRequest request){
        return ResponseEntity.ok(authService.refresh(request));
    }

}
