package com.appsolute.soomapi.domain.dormitory.study.exception;

import com.appsolute.soomapi.global.error.data.type.ErrorCode;
import com.appsolute.soomapi.global.error.exception.GlobalException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;


@Getter
public class ReserveNotFoundException extends GlobalException {
    public ReserveNotFoundException(String data) {
        super(ErrorCode.RESERVE_REQUEST_NOT_FOUND, data);
    }
}
