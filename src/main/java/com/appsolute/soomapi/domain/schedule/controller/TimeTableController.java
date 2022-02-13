package com.appsolute.soomapi.domain.schedule.controller;


import com.appsolute.soomapi.domain.schedule.service.TimeTableService;
import lombok.RequiredArgsConstructor;
import neiseApi.payload.sche.ScheReturnResponseDayDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/schedule/timetable")
@RequiredArgsConstructor
public class TimeTableController {
    private final TimeTableService timeTableService;

    //학생만 이용 가능. 선생 권한으로 신청 시 에러 리턴

    @GetMapping
    public List<ScheReturnResponseDayDto> getTimeTable() {
        return timeTableService.getSchedule();
    }

    @GetMapping("/between")
    public List<ScheReturnResponseDayDto> getTimeTableWithDate(@RequestParam Integer startDate, @RequestParam Integer endDate) {
        return timeTableService.getScheduleByDate(startDate, endDate);
    }



}
