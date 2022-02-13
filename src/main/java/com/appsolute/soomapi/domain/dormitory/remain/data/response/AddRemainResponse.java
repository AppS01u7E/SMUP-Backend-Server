package com.appsolute.soomapi.domain.dormitory.remain.data.response;

import com.appsolute.soomapi.domain.dormitory.remain.data.entity.RemainType;
import com.appsolute.soomapi.global.school.data.type.SchoolType;

import java.time.LocalDate;

public record AddRemainResponse(
        Long remainId,
        String accountUUID,
        LocalDate remainAt,
        SchoolType school,
        RemainType remainType
) {
}
