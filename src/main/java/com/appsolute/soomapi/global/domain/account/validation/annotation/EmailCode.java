package com.appsolute.soomapi.global.domain.account.validation.annotation;

import com.appsolute.soomapi.global.domain.account.validation.validator.EmailCodeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmailCodeValidator.class)
public @interface EmailCode {
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    String message() default "이메일 인증 코드 정책을 위반하였습니다!";
}
