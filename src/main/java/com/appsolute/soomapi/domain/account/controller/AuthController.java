package com.appsolute.soomapi.domain.account.controller;

import com.appsolute.soomapi.domain.account.data.response.AuthorizeEmailByCodeResponse;
import com.appsolute.soomapi.domain.account.data.response.GenerateTeacherSignupCodeResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

//계정 인증 관리 - 인증 API
@RestController
@RequestMapping("/api/v1/account/auth")
public class AuthController {
    @PostMapping("/email") //이메일 인증 수행
    public ResponseEntity<?> sendAuthorizeCodeToEmail(@RequestParam String email) {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/email/{code}") //이메일 인증 완료
    public ResponseEntity<AuthorizeEmailByCodeResponse> authorizeEmailByCode(@PathVariable String code) {
        AuthorizeEmailByCodeResponse response = new AuthorizeEmailByCodeResponse("dummy email token");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/code/teacher/{quantity}") //교사 회원가입 코드 생성
    public ResponseEntity<GenerateTeacherSignupCodeResponse> generateTeacherSignupCode(@PathVariable Integer quantity) {
        List<String> codes = IntStream.range(0, quantity).mapToObj(i -> "it is code that idx is " + i).collect(Collectors.toList());
        GenerateTeacherSignupCodeResponse response = new GenerateTeacherSignupCodeResponse(codes);
        return ResponseEntity.ok(response);
    }
}
