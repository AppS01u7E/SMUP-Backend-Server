package com.appsolute.soomapi.domain.schedule.service;

import neiseApi.payload.sche.ScheReturnResponseDayDto;

import java.util.List;

public interface TimeTableService {

    ScheReturnResponseDayDto getSchedule();
    List<ScheReturnResponseDayDto> getScheduleByDate(Integer startDate, Integer endDate);

}
