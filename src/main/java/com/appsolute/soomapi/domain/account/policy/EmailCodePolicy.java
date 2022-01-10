package com.appsolute.soomapi.domain.account.policy;

import com.appsolute.soomapi.global.policy.Policy;

public class EmailCodePolicy implements Policy<String> {
    @Override
    public boolean checkPolicy(String code) {
        if(code == null || code.length() != 6) return false; //만약 code 가 null 이거나 6자리가 아닌 문자열일 경우
        try {
            Integer.parseInt(code);
        } catch (NumberFormatException e) {
            return false; //만약 code 가 숫자 이외의 문자를 포함할 경우
        }
        return true; //만약 code 가 숫자로 이루어진 6자리 문자열일 경우
    }
}
