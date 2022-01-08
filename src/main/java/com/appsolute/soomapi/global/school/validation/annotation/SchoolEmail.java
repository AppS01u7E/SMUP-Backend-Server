package com.appsolute.soomapi.global.school.validation.annotation;

import com.appsolute.soomapi.global.school.type.DepartmentType;
import com.appsolute.soomapi.global.school.validation.validator.SchoolEmailValidator;

import javax.validation.Constraint;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SchoolEmailValidator.class)
public @interface SchoolEmail {
    DepartmentType[] allows() default {DepartmentType.BUSAN, DepartmentType.DAEDEOK, DepartmentType.DAEGU, DepartmentType.GWANGJU};
    Class[] groups() default {};
    Class[] payload() default {};
}
