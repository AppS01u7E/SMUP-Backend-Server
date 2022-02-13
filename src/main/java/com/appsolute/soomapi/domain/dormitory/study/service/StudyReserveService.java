package com.appsolute.soomapi.domain.dormitory.study.service;

import com.appsolute.soomapi.domain.dormitory.study.data.dto.ReserveDto;
import com.appsolute.soomapi.domain.dormitory.study.data.response.ReserveResponse;
import com.appsolute.soomapi.global.school.data.type.SchoolType;

import java.util.List;

public interface StudyReserveService {
    ReserveDto reserve(SchoolType type, String accountUUID, Integer roomNum,Integer seatNum);

    ReserveDto getReserve(String accountUUID);

    void cancelReserve(String accountUUID);

    List<ReserveResponse> getAllAccountUUIDAtReserve(SchoolType school, Integer roomNum);
}
