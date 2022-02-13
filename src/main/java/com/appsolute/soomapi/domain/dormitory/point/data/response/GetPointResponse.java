package com.appsolute.soomapi.domain.dormitory.point.data.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GetPointResponse(
        @JsonProperty("accountUUID") String accountUUID,
        @JsonProperty("rewardPoint") Integer rewardPoint,
        @JsonProperty("penaltyPoint") Integer penaltyPoint
) {
}
