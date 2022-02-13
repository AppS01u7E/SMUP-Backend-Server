package com.appsolute.soomapi.domain.dormitory.study.data.response;

public record IsReserveResponse(
        Long reserveId,
        Integer roomNum,
        Integer seatNum
) {
}
