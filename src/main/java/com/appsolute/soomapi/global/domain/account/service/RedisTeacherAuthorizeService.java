package com.appsolute.soomapi.global.domain.account.service;

import com.appsolute.soomapi.global.domain.account.strategy.TeacherSignupCodeExpiredStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
//Redis 로  교사 회원가입 코드를 저장하는 교사 인증 서비스
public class RedisTeacherAuthorizeService implements TeacherAuthorizeService{
    private static final String KEY_PREFIX = "teacher-signup-code"; //다른 용도로 redis 가 쓰일 수 있으므로, prefix 로 key 값을 구분한다.
    private final RedisTemplate<String, String> redisTemplate;
    private final TeacherSignupCodeExpiredStrategy expiredStrategy;

    private final Random random = new Random();

    @Override
    public List<String> generateTeacherCode(Integer quantity) {
        long expiredAt = expiredStrategy.getExpiredDateToLong(LocalDateTime.now()); //해당 코드의 유효기간을 구한다.
        return IntStream.range(0, quantity).mapToObj(i -> {
            String code = generateTeacherCode();
            redisTemplate.opsForValue().set(KEY_PREFIX + code, String.valueOf(expiredAt)); //유효기간을 value 로 하는 코드를 저장한다 (사실 value 에 쓸게 없어요)
            return code;
        }).collect(Collectors.toList());
    }

    //교사인증코드를 Redis 에 저장하고 반환한다.
    private String generateTeacherCode() {
        //A 부터 Z 까지의 랜덤한 문자로 이루어진 4자리 문자열
        return IntStream.range(0, 4)
                .mapToObj(x -> (char) (random.nextInt(26) + 'A'))
                .map(c -> Character.toString(c))
                .collect(Collectors.joining(""));
    }
}
