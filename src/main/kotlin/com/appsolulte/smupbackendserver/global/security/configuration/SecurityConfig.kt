package com.appsolulte.smupbackendserver.global.configuration

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


@EnableWebSecurity
@Configuration
class SecurityConfig: WebSecurityConfigurerAdapter() {

//    @Autowired
//    private lateinit var tokenProvider: TokenProvider

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Throws(Exception::class)
    override fun configure(web: WebSecurity) {
        web.ignoring().antMatchers("/static/css/**, /static/js/**, *.ico")
        web.ignoring().antMatchers(
            "/v3/api-docs", "/configuration/ui", "/swagger-resources",
            "/configuration/security", "/swagger-ui.html", "/webjars/**", "/swagger/**", "/api/auth/admin"
        )
    }


    @Throws(Exception::class)
    override fun configure(httpSecurity: HttpSecurity) {
        httpSecurity
            .csrf().disable()
            .cors().disable()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            .antMatchers(
                "/api/**").permitAll()
            .antMatchers("/api/auth/code").hasRole("ADMIN")
            .anyRequest().permitAll()
            .and()
//            .addFilterBefore(JwtFilter(tokenProvider), UsernamePasswordAuthenticationFilter::class.java)
    }

}