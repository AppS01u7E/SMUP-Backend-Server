package com.appsolute.soomapi.domain.schedule.exception;

import com.appsolute.soomapi.global.error.data.type.ErrorCode;
import com.appsolute.soomapi.global.error.exception.GlobalException;
import org.jetbrains.annotations.NotNull;

public class TaskNotFoundException extends GlobalException {
    public TaskNotFoundException(@NotNull String data) {
        super(ErrorCode.TASK_NOT_FOUND, data);
    }
}
