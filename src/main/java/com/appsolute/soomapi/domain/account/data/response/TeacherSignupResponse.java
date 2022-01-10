package com.appsolute.soomapi.domain.account.data.response;

import com.appsolute.soomapi.global.data.type.GenderType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import java.time.LocalDate;

@Getter
@RequiredArgsConstructor
public class TeacherSignupResponse {
    @Min(0)
    private final Long id;
    private final String emailToken;
    private final String firstName;
    private final String lastName;
    private final GenderType gender;
    @Range(min = 1, max = 3)
    private final Integer grade;
    @DateTimeFormat(pattern = "yyMMdd")
    private final LocalDate birth;
    private final String encodedPassword;
}
