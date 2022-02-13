package com.appsolute.soomapi.domain.dormitory.study.data.response;


import com.appsolute.soomapi.global.school.data.type.SchoolType;

import java.time.LocalDate;
import java.util.List;

public record GetReserveListResponse(
        SchoolType school,
        LocalDate reserveAt,
        List<ReserveResponse> accountUUIDList
) {
}
