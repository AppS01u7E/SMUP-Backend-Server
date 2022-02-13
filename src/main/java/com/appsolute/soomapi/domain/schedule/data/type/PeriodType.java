package com.appsolute.soomapi.domain.schedule.data.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum PeriodType {
    DAY(0), ONE(1), TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8), NINE(9), TEN(10), ELEVEN(11);

    private final Integer id;

    public static PeriodType of(Integer period) {
        for (PeriodType value : values())
            if(value.id.equals(period)) return value;
        throw new RuntimeException("PeriodType 을 찾을 수 없습니다! input : " + period);
    }
}
