package com.appsolute.soomapi.global.domain.account.controller;

import com.appsolute.soomapi.global.domain.account.data.request.StudentSignupRequest;
import com.appsolute.soomapi.global.domain.account.data.request.TeacherSignupRequest;
import com.appsolute.soomapi.global.domain.account.data.response.StudentSignupResponse;
import com.appsolute.soomapi.global.domain.account.data.response.TeacherSignupResponse;
import com.appsolute.soomapi.global.domain.account.data.response.StudentSignupResponse;
import com.appsolute.soomapi.global.domain.account.data.response.TeacherSignupResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//계정 인증 관리 - 회원가입 API
@RestController
@RequestMapping("/api/v1/account/signup")
public class SignupController {
    @PostMapping("/teacher") //교사 회원가입
    public ResponseEntity<TeacherSignupResponse> teacherSignup(@RequestBody TeacherSignupRequest request) {
        return null;
    }

    @PostMapping("/student") //학생 회원가입
    public ResponseEntity<StudentSignupResponse> studentSignup(@RequestBody StudentSignupRequest request) {
        return null;
    }
}
