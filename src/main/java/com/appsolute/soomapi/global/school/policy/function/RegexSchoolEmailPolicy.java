package com.appsolute.soomapi.global.school.policy.function;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RegexSchoolEmailPolicy implements SchoolEmailPolicy{
    private final String regex;

    @Override
    public boolean checkPolicy(String email) {
        if(email == null) return false;
        return email.matches(regex);
    }
}
