package com.appsolute.soomapi.global.school.validation.validator;

import com.appsolute.soomapi.global.school.data.type.SchoolType;
import com.appsolute.soomapi.global.school.validation.annotation.SchoolEmail;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SchoolEmailValidator implements ConstraintValidator<SchoolEmail, String>{
    private SchoolType[] allows;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        for (SchoolType allow : allows) {
            if(allow.checkPolicy(value)) return true;
        }
        return false; //이메일이 검증대상 학교(allows)들의 교내 이메일 정책을 하나도 통과하지 못할경우
    }

    @Override
    public void initialize(SchoolEmail constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        allows = constraintAnnotation.allows();
    }
}
