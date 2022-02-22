package com.appsolute.soomapi.domain.account.controller;

import com.appsolute.soomapi.domain.account.data.request.EmailCodeRequest;
import com.appsolute.soomapi.domain.account.data.request.EmailRequest;
import com.appsolute.soomapi.domain.account.data.response.AuthorizeEmailByCodeResponse;
import com.appsolute.soomapi.domain.account.data.response.GenerateTeacherSignupCodeResponse;
import com.appsolute.soomapi.domain.account.service.EmailAuthorizeService;
import com.appsolute.soomapi.domain.account.service.TeacherAuthorizeService;
import com.appsolute.soomapi.domain.account.util.EmailJwtUtils;
import com.appsolute.soomapi.domain.account.validation.annotation.EmailCode;
import com.appsolute.soomapi.global.error.data.type.ErrorCode;
import com.appsolute.soomapi.global.error.exception.GlobalException;
import com.appsolute.soomapi.global.school.validation.annotation.SchoolEmail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

//계정 인증 관리 - 인증 API
@RequiredArgsConstructor
@RestController
@Validated
@RequestMapping("/api/v1/account/cert")
@Slf4j
public class CertController {
    private final EmailAuthorizeService emailAuthorizeService;
    private final TeacherAuthorizeService teacherAuthorizeService;
    private final EmailJwtUtils emailJwtUtils;

    @PostMapping //이메일 인증 수행
    public ResponseEntity<?> sendAuthorizeCodeToEmail(@RequestBody  EmailRequest request) {
        log.info(request.getEmail()); //@SchoolEmail 수정 후 추후 재설정
        //랜덤한 6자리 숫자로 이루어진 인증코드를 생성한다 (이때, 인증코드는 문자열 형식으로 저장된다)
        String code = emailAuthorizeService.generateAuthorizeCode();
        //이메일 인증 정보를 저장한다
        emailAuthorizeService.addAuthorizeData(code, request.getEmail());
        //인증코드를 포함한 인증메일을 인자로 받은 email 로 송신한다
        emailAuthorizeService.sendAuthorizeEmail(code, request.getEmail());

        return ResponseEntity.ok().build();
    }

    @GetMapping("/email/{code}") //이메일 인증 완료
    public ResponseEntity<AuthorizeEmailByCodeResponse> authorizeEmailByCode(@PathVariable String code) {
        //코드를 통해 이메일을 가져온다.(인증) // @EmailCode 수정 후 추후 재설정
        String email = emailAuthorizeService.getEmail(code);
        //가져온 이메일을 통해 이메일 토큰을 생성한다.
        String emailToken = emailAuthorizeService.generateEmailToken(email);
        //생성한 이메일 토큰을 Response 에 담아서 반환한다.
        AuthorizeEmailByCodeResponse response = new AuthorizeEmailByCodeResponse(emailToken);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/code/teacher/{quantity}") //교사 회원가입 코드 생성
    public ResponseEntity<GenerateTeacherSignupCodeResponse> generateTeacherSignupCode(@PathVariable @Min(0) @Max(100) Integer quantity) {
        //quantity 만큼의 교사 회원가입 코드를 생성한다.
        List<String> codes = teacherAuthorizeService.generateTeacherCode(quantity);
        //생성한 교사 회원가입 코드를 Response 에 담아서 반환한다.
        GenerateTeacherSignupCodeResponse response = new GenerateTeacherSignupCodeResponse(codes);
        return ResponseEntity.ok(response);
    }
}
