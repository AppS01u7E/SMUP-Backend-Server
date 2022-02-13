package com.appsolute.soomapi.domain.dormitory.remain.data.dto;

import com.appsolute.soomapi.domain.dormitory.remain.data.entity.RemainType;
import com.appsolute.soomapi.domain.dormitory.remain.data.response.GetRemainListResponse;
import com.appsolute.soomapi.domain.dormitory.remain.data.response.ShortnessRemainResponse;
import com.appsolute.soomapi.global.school.data.type.SchoolType;

import java.time.LocalDate;

public record RemainDto(
        Long id,
        String accountUUID,
        LocalDate remainAt,
        SchoolType school,
        RemainType remainType
) {
    public ShortnessRemainResponse toShortnessRemainResponse() {
        return new ShortnessRemainResponse(
                accountUUID,
                remainType
        );
    }

}
