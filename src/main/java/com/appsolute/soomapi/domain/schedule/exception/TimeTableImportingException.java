package com.appsolute.soomapi.domain.schedule.exception;

import com.appsolute.soomapi.global.error.data.type.ErrorCode;
import com.appsolute.soomapi.global.error.exception.GlobalException;
import org.jetbrains.annotations.NotNull;

public class TimeTableImportingException extends GlobalException {
    public TimeTableImportingException() {
        super(ErrorCode.TIMETABLE_IMPORTING_EXCEPTION, "none");
    }
}
