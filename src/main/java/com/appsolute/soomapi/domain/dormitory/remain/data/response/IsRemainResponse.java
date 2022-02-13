package com.appsolute.soomapi.domain.dormitory.remain.data.response;

import com.appsolute.soomapi.domain.dormitory.remain.data.entity.RemainType;

public record IsRemainResponse(
        Long remainId,
        RemainType remainType
) {
}
