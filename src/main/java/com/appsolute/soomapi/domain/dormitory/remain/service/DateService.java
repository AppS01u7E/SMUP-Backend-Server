package com.appsolute.soomapi.domain.dormitory.remain.service;

import java.time.LocalDate;

public interface DateService {
    //다음 잔류 시작일을 구한다
    LocalDate getRemainStartDay();
}
