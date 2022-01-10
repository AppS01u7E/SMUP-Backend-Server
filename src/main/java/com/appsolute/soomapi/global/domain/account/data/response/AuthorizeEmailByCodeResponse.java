package com.appsolute.soomapi.global.domain.account.data.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

//이메일 인증 완료 Feature 에 대한 Positive Response
@Getter
@RequiredArgsConstructor
public final class AuthorizeEmailByCodeResponse {
    @JsonProperty("emailToken")
    private final String emailToken; //인증된 이메일의 정보를 담고있는 jwt token
}
