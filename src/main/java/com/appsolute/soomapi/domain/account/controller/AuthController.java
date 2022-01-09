package com.appsolute.soomapi.domain.account.controller;

import com.appsolute.soomapi.domain.account.data.response.AuthorizeEmailByCodeResponse;
import com.appsolute.soomapi.domain.account.data.response.GenerateTeacherSignupCodeResponse;
import com.appsolute.soomapi.domain.account.service.EmailAuthorizeService;
import com.appsolute.soomapi.domain.account.service.TeacherAuthorizeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//계정 인증 관리 - 인증 API
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/account/auth")
public class AuthController {
    private final EmailAuthorizeService emailAuthorizeService;
    private final TeacherAuthorizeService teacherAuthorizeService;

    //TODO [지인호] school email validation 추가
    @PostMapping("/email") //이메일 인증 수행
    public ResponseEntity<?> sendAuthorizeCodeToEmail(@RequestParam String email) {
        //랜덤한 6자리 숫자로 이루어진 인증코드를 생성한다 (이때, 인증코드는 문자열 형식으로 저장된다)
        String code = emailAuthorizeService.generateAuthorizeCode();
        //이메일 인증 정보를 저장한다
        emailAuthorizeService.addAuthorizeData(code, email);
        //인증코드를 포함한 인증메일을 인자로 받은 email 로 송신한다
        emailAuthorizeService.sendAuthorizeEmail(code, email);
        return ResponseEntity.ok().build();
    }

    //TODO [지인호] code validation 추가
    @GetMapping("/email/{code}") //이메일 인증 완료
    public ResponseEntity<AuthorizeEmailByCodeResponse> authorizeEmailByCode(@PathVariable String code) {
        //코드를 통해 이메일을 가져온다.(인증)
        String email = emailAuthorizeService.getEmail(code);
        //가져온 이메일을 통해 이메일 토큰을 생성한다.
        String emailToken = emailAuthorizeService.generateEmailToken(email);
        //생성한 이메일 토큰을 Response 에 담아서 반환한다.
        AuthorizeEmailByCodeResponse response = new AuthorizeEmailByCodeResponse(emailToken);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/code/teacher/{quantity}") //교사 회원가입 코드 생성
    public ResponseEntity<GenerateTeacherSignupCodeResponse> generateTeacherSignupCode(@PathVariable Integer quantity) {
        //quantity 만큼의 교사 회원가입 코드를 생성한다.
        List<String> codes = teacherAuthorizeService.generateTeacherCode(quantity);
        //생성한 교사 회원가입 코드를 Response 에 담아서 반환한다.
        GenerateTeacherSignupCodeResponse response = new GenerateTeacherSignupCodeResponse(codes);
        return ResponseEntity.ok(response);
    }
}
