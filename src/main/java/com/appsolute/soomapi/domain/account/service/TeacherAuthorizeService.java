package com.appsolute.soomapi.domain.account.service;

import java.util.List;

public interface TeacherAuthorizeService {
    List<String> generateTeacherCode(Integer quantity);
}
