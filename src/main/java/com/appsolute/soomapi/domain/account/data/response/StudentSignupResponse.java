package com.appsolute.soomapi.domain.account.data.response;

import com.appsolute.soomapi.global.data.type.GenderType;
import com.appsolute.soomapi.global.school.data.type.DepartmentType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Getter
@RequiredArgsConstructor
public class StudentSignupResponse {
    private final Long id;
    private final String email;
    private final String firstName;
    private final String lastName;
    private final GenderType gender;
    private final Integer grade;
    private final LocalDate birth;
    private final String rawPassword;
}
