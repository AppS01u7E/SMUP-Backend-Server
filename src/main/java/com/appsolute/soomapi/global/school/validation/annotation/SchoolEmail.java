package com.appsolute.soomapi.global.school.validation.annotation;

import com.appsolute.soomapi.global.school.data.type.SchoolType;
import com.appsolute.soomapi.global.school.validation.validator.SchoolEmailValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SchoolEmailValidator.class)
public @interface SchoolEmail {
    SchoolType[] allows() default {SchoolType.BUSAN, SchoolType.DAEDEOK, SchoolType.DAEGU, SchoolType.GWANGJU};
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    String message() default "해당 리전의 학교이메일이 아닙니다!";
}
