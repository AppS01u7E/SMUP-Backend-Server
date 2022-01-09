package com.appsolute.soomapi.domain.account.data.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public final class GenerateTeacherSignupCodeResponse {
    @JsonProperty("code")
    private final List<String> code;

    //객체의 불변성을 지키기 위해 custom getter 구현
    public List<String> getCode() {
        return List.copyOf(code);
    }
}
