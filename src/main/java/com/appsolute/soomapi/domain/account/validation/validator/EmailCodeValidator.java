package com.appsolute.soomapi.domain.account.validation.validator;

import com.appsolute.soomapi.domain.account.policy.EmailCodePolicy;
import com.appsolute.soomapi.domain.account.validation.annotation.EmailCode;

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
