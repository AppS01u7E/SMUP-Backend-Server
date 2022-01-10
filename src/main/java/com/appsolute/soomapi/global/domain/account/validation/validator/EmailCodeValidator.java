package com.appsolute.soomapi.global.domain.account.validation.validator;

import com.appsolute.soomapi.global.domain.account.policy.EmailCodePolicy;
import com.appsolute.soomapi.global.domain.account.validation.annotation.EmailCode;
import com.appsolute.soomapi.global.domain.account.policy.EmailCodePolicy;
import com.appsolute.soomapi.global.domain.account.validation.annotation.EmailCode;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailCodeValidator implements ConstraintValidator<EmailCode, String> {
    private final EmailCodePolicy policy;

    public EmailCodeValidator() {
        policy = new EmailCodePolicy();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return policy.checkPolicy(value);
    }
}
