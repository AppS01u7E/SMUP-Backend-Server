package com.appsolute.soomapi.domain.account.service;

//이메일 인증에 관한 기능을 담당하는 서비스
public interface EmailAuthorizeService {
    //랜덤한 6자리 숫자로 이루어진 인증코드를 생성한다
    String generateAuthorizeCode();
    //인증이 진행되고 있는 이메일에 대한 정보를 저장한다.
    void addAuthorizeData(String code, String email);
    //인증메일을 송신한다.
    void sendAuthorizeEmail(String code, String email);
}
