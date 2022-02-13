package com.appsolute.soomapi.domain.schedule.data.request;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public record AddScheduleRequest(
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd", timezone = "Asia/Seoul")
        LocalDate date,
        Integer period,
        String message
) {}
