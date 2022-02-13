package com.appsolute.soomapi.domain.schedule.data.request;

import com.appsolute.soomapi.domain.schedule.data.type.PeriodType;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record UpdateTaskRequest(
        @DateTimeFormat(pattern = "yyyyMMdd")
        String date,
        PeriodType period,
        String message
) {}
