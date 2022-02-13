package com.appsolute.soomapi.domain.schedule.data.request;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddGroupTaskRequest extends AddTaskRequest {
    private String groupId;

}
