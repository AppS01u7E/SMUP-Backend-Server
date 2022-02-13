package com.appsolute.soomapi.domain.dormitory.study.exception;

import com.appsolute.soomapi.global.error.data.type.ErrorCode;
import com.appsolute.soomapi.global.error.exception.GlobalException;
import org.jetbrains.annotations.NotNull;

public class AlreadySomeoneReservedSeatException extends GlobalException{
    public AlreadySomeoneReservedSeatException(String data) {
        super(ErrorCode.SOMEONE_RESERVED_SEAT, data);
    }
}
