package com.appsolute.soomapi.domain.schedule.data.response;

import com.appsolute.soomapi.domain.schedule.data.dto.TaskDto;

import java.util.List;

public record GetMonthlyScheduleResponse(
        Integer year,
        Integer month,
        Integer pageSize,
        Integer pageNum,
        List<TaskDto> tasks
) {}
