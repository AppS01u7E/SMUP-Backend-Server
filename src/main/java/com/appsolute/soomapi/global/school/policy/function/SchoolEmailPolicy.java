package com.appsolute.soomapi.global.school.policy.function;

import com.appsolute.soomapi.global.policy.Policy;

public interface SchoolEmailPolicy extends Policy<String> {
    boolean checkPolicy(String email);
}
