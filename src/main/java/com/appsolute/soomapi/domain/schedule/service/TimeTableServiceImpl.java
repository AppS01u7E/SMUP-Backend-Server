package com.appsolute.soomapi.domain.schedule.service;

import com.appsolute.soomapi.domain.account.data.entity.user.Student;
import com.appsolute.soomapi.domain.account.data.entity.user.User;
import com.appsolute.soomapi.domain.schedule.exception.TimeTableImportingException;
import com.appsolute.soomapi.global.env.property.NeisProperty;
import com.appsolute.soomapi.global.security.CurrentUser;
import lombok.RequiredArgsConstructor;
import neiseApi.Neis;
import neiseApi.School;
import neiseApi.payload.sche.ScheReturnResponseDayDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;


@RequiredArgsConstructor
@Service
public class TimeTableServiceImpl implements TimeTableService {
    private NeisProperty neisProperty;
    @Value("${neis.secretKey}")
    private String NEISKEY;

    private School school = new School(NEISKEY);
    private final CurrentUser current;

    @Override
    public List<ScheReturnResponseDayDto> getSchedule() {

        Student std = current.getStudent();
        try {
            Integer today = Integer.valueOf(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
            return school.getSchoolSchedule(std.getSchool().getSD_SCHUL_CODE(), std.getGrade(), std.getClass(), today, today);
        } catch (IOException e){
            throw new TimeTableImportingException();
        }
    }

    @Override
    public List<ScheReturnResponseDayDto> getScheduleByDate(Integer startDate, Integer endDate) {
        Student std = current.getStudent();
        try {
            return school.getSchoolSchedule(std.getSchool().getSD_SCHUL_CODE(), std.getGrade(), std.getClass(), startDate, endDate);
        } catch (IOException e){
            throw new TimeTableImportingException();
        }
    }


}
