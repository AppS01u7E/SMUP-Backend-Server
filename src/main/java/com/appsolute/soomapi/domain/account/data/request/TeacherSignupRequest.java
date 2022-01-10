package com.appsolute.soomapi.domain.account.data.request;

import com.appsolute.soomapi.domain.account.data.type.TeacherType;
import com.appsolute.soomapi.global.school.data.type.GenderType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@RequiredArgsConstructor
public class TeacherSignupRequest {
    private final String emailToken;
    private final String firstName;
    private final String lastName;
    private final GenderType gender;
    private final Integer grade;
    @DateTimeFormat(pattern = "yyMMdd")
    private final LocalDate birth;
    private final String rawPassword;
    private final TeacherType teacherType;
    private final String major;
    private final String code;
}
