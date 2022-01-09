package com.appsolute.soomapi.global.school.policy.function;

public class DomainSchoolEmailPolicy extends RegexSchoolEmailPolicy {
    public DomainSchoolEmailPolicy(String domain) {
        super(".+@" + domain);
    }
}
