package com.appsolute.soomapi.global.policy;

public interface Policy<T> {
    boolean checkPolicy(T value);
}
