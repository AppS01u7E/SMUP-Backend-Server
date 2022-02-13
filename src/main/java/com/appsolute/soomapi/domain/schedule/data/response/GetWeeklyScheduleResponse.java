package com.appsolute.soomapi.domain.schedule.data.response;

import com.appsolute.soomapi.domain.schedule.data.dto.TaskDto;

import java.util.List;

public record GetWeeklyScheduleResponse(
        Integer year,
        Integer month,
        Integer week,
        Integer pageSize,
        Integer pageNum,
        List<TaskDto> tasks
) {}
