package com.appsolute.soomapi.domain.dormitory.point.service;

import com.appsolute.soomapi.domain.dormitory.point.data.dto.PointDto;
import com.appsolute.soomapi.domain.dormitory.point.data.type.PointType;

public interface PointService {
    PointDto getPointByAccountUUID(String accountUUID);

    PointDto updatePoint(String accountUUID, PointType type, Integer point);

}
