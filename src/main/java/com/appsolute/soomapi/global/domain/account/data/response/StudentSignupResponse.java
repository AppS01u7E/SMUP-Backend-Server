package com.appsolute.soomapi.global.domain.account.data.response;

import com.appsolute.soomapi.global.school.data.type.GenderType;
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
