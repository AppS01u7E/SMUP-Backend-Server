package com.appsolute.soomapi.global.domain.account.strategy;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Component
public class TeacherSignupCodeExpiredStrategyImpl implements TeacherSignupCodeExpiredStrategy{
    @Override
    public long getExpiredDateToLong(LocalDateTime datetime) {
        return datetime.plusDays(7).toLocalDate()//토큰은 일주일간 유효하다
                .atStartOfDay(ZoneId.of("Asia/Seoul"))//UTC + 9
                .toInstant().toEpochMilli(); //일주일 후 0시 0분의 시각을 밀리초로 변환한다.
    }
}
