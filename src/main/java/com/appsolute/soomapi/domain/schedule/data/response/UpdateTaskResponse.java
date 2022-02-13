package com.appsolute.soomapi.domain.schedule.data.response;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record UpdateTaskResponse(
        Long uuid,
        String date,
        Integer period,
        String message) {
}
