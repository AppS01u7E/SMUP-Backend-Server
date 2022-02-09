package com.appsolute.soomapi.domain.account.service;

import com.appsolute.soomapi.domain.account.util.EmailJwtUtils;
import com.appsolute.soomapi.infra.service.MailSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

//Redis 로 인증 정보를 저장하는 이메일 인증 서비스
@Service
@RequiredArgsConstructor
public class RedisEmailAuthorizeService implements EmailAuthorizeService {
    private static final String KEY_PREFIX = "email-authorize-code"; //다른 용도로 redis 가 쓰일 수 있으므로, prefix 로 key 값을 구분한다.
    private final RedisTemplate<String, String> redisTemplate;
    private final MailSenderService mailSenderService;
    private final EmailJwtUtils emailJwtUtils;

    private final Random random = new Random();

    @Override
    public String generateAuthorizeCode() {
        Integer randomNumber = random.nextInt(999999); //0 ~ 999999 범위 내의 랜덤한 숫자를 구한다.
        return String.format("%06d", randomNumber); //서식 문자열을 통해 숫자를 6자리로 formatting 한 후 반환한다.
    }

    @Override
    public void addAuthorizeData(final String code, final String email) {
        //redisTemplate 를 통해 Redis 에 데이터를 저장한다.
        //데이터의 key 는 code, value 는 email 이다.
        redisTemplate.opsForValue().set(KEY_PREFIX + code, email);
    }

    @Override
    public String getEmail(final String code) {
        //redisTemplate 를 통해 Redis 에서 데이터를 조회한다.
        //code 를 key 로 하는 value(email) 를 반환한다.
        return redisTemplate.opsForValue().get(KEY_PREFIX + code);
    }

    private static final String AUTHORIZE_MAIL_TITLE = "[SMUP] 인증번호가 도착했어요!";
    @Override
    public void sendAuthorizeEmail(final String code, final String email) {
        Map<String, Object> model = new HashMap<>();
        model.put("code", code);
        mailSenderService.sendHtmlEmail(email, AUTHORIZE_MAIL_TITLE, "authorize/mail", model);
    }

    @Override
    public String generateEmailToken(final String email) {
        return emailJwtUtils.encodeToken(email);
    }
}

