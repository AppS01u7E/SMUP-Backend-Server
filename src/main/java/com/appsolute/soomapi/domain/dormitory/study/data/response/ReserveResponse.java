package com.appsolute.soomapi.domain.dormitory.study.data.response;


import com.appsolute.soomapi.global.school.data.type.SchoolType;

import java.time.LocalDate;

public record ReserveResponse(
        Long reserveId,
        String accountUUID,
        LocalDate reserveAt,
        Integer roomNum,
        Integer seatNum
) {
}
