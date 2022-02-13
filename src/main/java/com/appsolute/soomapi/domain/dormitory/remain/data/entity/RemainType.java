package com.appsolute.soomapi.domain.dormitory.remain.data.entity;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RemainType {
    FRI_TO_SAT("금요일 귀가. 토요일 귀사"),
    FRI_TO_SUN("금요일 귀가. 일요일 귀사"),
    SAT_TO_SUN("토요일 귀가. 일요일 귀사"),
    REMAIN("잔류");


    private final String description;

}
