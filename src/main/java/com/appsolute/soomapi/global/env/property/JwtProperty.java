package com.appsolute.soomapi.global.env.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("jwt")
@Getter @Setter
public class JwtProperty {
    private String secret;
    private Long expiredAt;
}