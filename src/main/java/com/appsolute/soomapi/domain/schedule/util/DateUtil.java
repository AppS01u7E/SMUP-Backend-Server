package com.appsolute.soomapi.domain.schedule.util;

import java.time.LocalDate;
import java.time.YearMonth;


public class DateUtil {
    //TODO 로직 재구성 필요
    public static LocalDate getLastDayOfWeek(Integer year, Integer month, Integer week) {
        return LocalDate.of(year, month, 1)
                .plusDays((week-1) * 7L)
                .plusDays(7 - LocalDate.of(year, month, 1).getDayOfWeek().getValue());
    }

    //TODO 로직 재구성 필요
    public static LocalDate getFirstDayOfWeek(Integer year, Integer month, Integer week) {
        if(week == 1) return getFirstDayOfMonth(year, month);
        else return getFirstDayOfMonth(year, month)
                .plusDays((week-1) * 7L)
                .minusDays(7 - LocalDate.of(year, month, 1).getDayOfWeek().getValue()); //
    }

    public static LocalDate getFirstDayOfMonth(Integer year, Integer month) {
        return LocalDate.of(year, month, 1);
    }
    public static LocalDate getLastDayOfMonth(Integer year, Integer month) {
        return YearMonth.of(year, month).atEndOfMonth();
    }
}
