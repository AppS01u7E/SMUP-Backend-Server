package com.appsolute.soomapi.domain.schedule.data.dto;


import com.appsolute.soomapi.domain.schedule.data.type.PeriodType;

import java.time.LocalDate;

public record TaskDto(
        Long UUID,
        String date,
        PeriodType period,
        String message
) {
}
