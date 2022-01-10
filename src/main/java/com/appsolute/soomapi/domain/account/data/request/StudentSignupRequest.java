package com.appsolute.soomapi.domain.account.data.request;

import com.appsolute.soomapi.global.data.type.GenderType;
import com.appsolute.soomapi.global.school.data.type.DepartmentType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Getter
@RequiredArgsConstructor
public class StudentSignupRequest {
    private final String emailToken;
    private final String firstName;
    private final String lastName;
    private final GenderType gender;
    private final LocalDate birth;
    private final DepartmentType dept;
    private final Integer grade;
    private final Integer clazz;
    private final Integer entYear;
    private final String rawPassword;
}
