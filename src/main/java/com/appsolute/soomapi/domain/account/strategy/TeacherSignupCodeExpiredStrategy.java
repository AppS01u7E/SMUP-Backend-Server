package com.appsolute.soomapi.domain.account.strategy;

import java.time.LocalDateTime;

public interface TeacherSignupCodeExpiredStrategy {
    long getExpiredDateToLong(LocalDateTime datetime);
}
