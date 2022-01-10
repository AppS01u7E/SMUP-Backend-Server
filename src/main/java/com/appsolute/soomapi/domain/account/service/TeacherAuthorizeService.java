package com.appsolute.soomapi.domain.account.service;

import java.util.List;

//교사 인증에 관한 기능을 담당하는 서비스
public interface TeacherAuthorizeService {
    List<String> generateTeacherCode(Integer quantity);
}
