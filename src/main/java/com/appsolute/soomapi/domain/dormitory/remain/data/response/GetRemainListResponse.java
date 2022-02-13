package com.appsolute.soomapi.domain.dormitory.remain.data.response;

import com.appsolute.soomapi.global.school.data.type.SchoolType;

import java.time.LocalDate;
import java.util.List;

public record GetRemainListResponse(
        LocalDate remainAt,
        SchoolType school,
        List<ShortnessRemainResponse> remainList
) {
}
