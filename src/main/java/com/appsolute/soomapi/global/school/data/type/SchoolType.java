package com.appsolute.soomapi.global.school.data.type;

import com.appsolute.soomapi.global.school.policy.BusanSchoolEmailPolicy;
import com.appsolute.soomapi.global.school.policy.DaedeokSchoolEmailPolicy;
import com.appsolute.soomapi.global.school.policy.DaeguSchoolEmailPolicy;
import com.appsolute.soomapi.global.school.policy.GwangjuSchoolEmailPolicy;
import com.appsolute.soomapi.global.school.policy.function.SchoolEmailPolicy;
import lombok.RequiredArgsConstructor;

import javax.annotation.Nullable;

@RequiredArgsConstructor
public enum SchoolType {
    BUSAN(new BusanSchoolEmailPolicy()),
    DAEDEOK(new DaedeokSchoolEmailPolicy()),
    DAEGU(new DaeguSchoolEmailPolicy()),
    GWANGJU(new GwangjuSchoolEmailPolicy());

    private final SchoolEmailPolicy policy;

    public boolean checkPolicy(@Nullable String email) {
        return policy.checkPolicy(email);
    }
}
