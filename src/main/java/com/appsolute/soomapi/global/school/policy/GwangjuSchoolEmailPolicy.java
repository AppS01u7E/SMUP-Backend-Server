package com.appsolute.soomapi.global.school.policy;

import com.appsolute.soomapi.global.school.policy.function.RegexSchoolEmailPolicy;

public class GwangjuSchoolEmailPolicy extends RegexSchoolEmailPolicy {
    public GwangjuSchoolEmailPolicy() {
        super("[st]\\d{5}@gsm.hs.kr");
    }
}
