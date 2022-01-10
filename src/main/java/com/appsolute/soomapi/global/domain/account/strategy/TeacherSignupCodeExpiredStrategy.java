package com.appsolute.soomapi.global.domain.account.strategy;

import java.time.LocalDateTime;

public interface TeacherSignupCodeExpiredStrategy {
    long getExpiredDateToLong(LocalDateTime datetime);
}
