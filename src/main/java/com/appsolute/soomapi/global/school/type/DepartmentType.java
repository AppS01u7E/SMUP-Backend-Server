package com.appsolute.soomapi.global.school.type;

import com.appsolute.soomapi.global.school.policy.*;
import com.appsolute.soomapi.global.school.policy.function.SchoolEmailPolicy;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum DepartmentType {
    BUSAN(new BusanSchoolEmailPolicy()),
    DAEDEOK(new DaedeokSchoolEmailPolicy()),
    DAEGU(new DaeguSchoolEmailPolicy()),
    GWANGJU(new GwangjuSchoolEmailPolicy());
    private final SchoolEmailPolicy policy;

    public boolean checkPolicy(String email) {
        return policy.checkPolicy(email);
    }
}
