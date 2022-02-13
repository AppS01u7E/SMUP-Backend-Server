package com.appsolute.soomapi.domain.dormitory.study.data.dto;

import com.appsolute.soomapi.global.school.data.type.SchoolType;

import java.time.LocalDate;

public record ReserveDto(
        Long id,
        String accountUUID,
        LocalDate reserveAt,
        SchoolType school,
        Integer roomNum,
        Integer seatNum
) {
}
