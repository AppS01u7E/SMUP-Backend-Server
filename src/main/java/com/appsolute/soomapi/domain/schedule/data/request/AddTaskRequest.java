package com.appsolute.soomapi.domain.schedule.data.request;

import com.appsolute.soomapi.domain.schedule.data.type.PeriodType;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class AddTaskRequest {
    private String accountUUID;
    private LocalDate date;
    private String title;
    private String message;
    private PeriodType period;

}

