package com.appsolute.soomapi.global.env.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Configuration(value = "non-authorize")
public class SecurityConfiguration  extends WebSecurityConfigurerAdapter {
    @Override //TODO [지인호] 2022 01 08 기준 아직 인증로직이 구현되지 않아, 인증 여부와 관계 없이 모든 자원에 대한 접근을 허용한다
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().and()
                .csrf().and()
                .sessionManagement().disable()
                .authorizeRequests()
                .antMatchers("/**").permitAll();
    }
}
