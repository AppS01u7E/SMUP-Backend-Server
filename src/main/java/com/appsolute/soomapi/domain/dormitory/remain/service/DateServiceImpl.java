package com.appsolute.soomapi.domain.dormitory.remain.service;

import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

@Service
public class DateServiceImpl implements DateService{
    @Override
    public LocalDate getRemainStartDay() {
        return LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.FRIDAY));
    }
}
