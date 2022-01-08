package com.appsolute.soomapi.domain.account.validation.validator;

import com.appsolute.soomapi.domain.account.policy.EmailCodePolicy;
import com.appsolute.soomapi.global.school.validation.annotation.SchoolEmail;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailCodeValidator implements ConstraintValidator<SchoolEmail, String> {
    private final EmailCodePolicy policy;

    public EmailCodeValidator() {
        policy = new EmailCodePolicy();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return policy.checkPolicy(value);
    }
}
