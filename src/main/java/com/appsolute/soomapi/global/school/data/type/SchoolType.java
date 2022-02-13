package com.appsolute.soomapi.global.school.data.type;

import com.appsolute.soomapi.global.policy.Policy;
import com.appsolute.soomapi.global.school.policy.BusanSchoolEmailPolicy;
import com.appsolute.soomapi.global.school.policy.DaedeokSchoolEmailPolicy;
import com.appsolute.soomapi.global.school.policy.DaeguSchoolEmailPolicy;
import com.appsolute.soomapi.global.school.policy.GwangjuSchoolEmailPolicy;
import com.appsolute.soomapi.global.school.policy.function.RegexSchoolEmailPolicy;
import com.appsolute.soomapi.global.school.policy.function.SchoolEmailPolicy;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.annotation.Nullable;

@RequiredArgsConstructor
public enum SchoolType {
    BUSAN(new BusanSchoolEmailPolicy(), "C10", "7150658"),
    DAEDEOK(new DaedeokSchoolEmailPolicy(), "G10", "7430310"),
    DAEGU(new DaeguSchoolEmailPolicy(), "D10", "7240454"),
    GWANGJU(new GwangjuSchoolEmailPolicy(), "F10", "7380292"),
    ELSE(new RegexSchoolEmailPolicy("d"), "What", "WHat");

    private final SchoolEmailPolicy policy;
    private final String ATPT_OFCDC_SC_CODE;
    private final String SD_SCHUL_CODE;

    public boolean checkPolicy(@Nullable String email) {
        return policy.checkPolicy(email);
    }
    public String getATPT_OFCDC_SC_CODE() {return this.ATPT_OFCDC_SC_CODE; }
    public String getSD_SCHUL_CODE() {return this.SD_SCHUL_CODE; }

}
