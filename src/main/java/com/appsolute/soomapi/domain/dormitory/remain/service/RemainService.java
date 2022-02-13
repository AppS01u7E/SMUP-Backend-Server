package com.appsolute.soomapi.domain.dormitory.remain.service;

import com.appsolute.soomapi.domain.dormitory.remain.data.dto.RemainDto;
import com.appsolute.soomapi.domain.dormitory.remain.data.entity.RemainType;
import com.appsolute.soomapi.global.school.data.type.SchoolType;

import java.util.List;

public interface RemainService {
    RemainDto addRemain(String accountUUID, SchoolType school, RemainType type);

    RemainDto getRemain(String accountUUID);

    List<RemainDto> getRemainList(SchoolType school);

    void removeRemain(String accountUUID);
}
