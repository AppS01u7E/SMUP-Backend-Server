package com.appsolute.soomapi.domain.schedule.data.response;

import java.time.LocalDate;

public record AddAccountResponse(
        Long UUID,
        String date,
        Integer period,
        String message
) {}
