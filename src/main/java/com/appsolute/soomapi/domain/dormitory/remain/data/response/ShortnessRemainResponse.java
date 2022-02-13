package com.appsolute.soomapi.domain.dormitory.remain.data.response;

import com.appsolute.soomapi.domain.dormitory.remain.data.entity.RemainType;

public record ShortnessRemainResponse(
        String accountUUID,
        RemainType remainType
) {

}
