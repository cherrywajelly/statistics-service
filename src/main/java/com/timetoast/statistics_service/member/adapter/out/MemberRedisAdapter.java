package com.timetoast.statistics_service.member.adapter.out;

import com.timetoast.statistics_service.member.domain.dto.MemberJoinDto;
import com.timetoast.statistics_service.member.port.out.MemberStore;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.format.DateTimeFormatter;

import static com.timetoast.statistics_service.global.constant.RedisKeyConstant.*;

@Repository
public class MemberRedisAdapter implements MemberStore {

    private final RedisTemplate<String, String> redisTemplate;

    public MemberRedisAdapter(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void saveJoinedMemberStats(MemberJoinDto memberJoinDto) {
        String monthKey = memberJoinDto.memberRole() + COLON.value() + MONTH_SIGNUP.value() + COLON.value()
                + memberJoinDto.joinDate().format(DateTimeFormatter.ofPattern("yyyy-MM"));

        String scoreKey = DAY.value()+COLON+memberJoinDto.joinDate().getDayOfMonth();

        redisTemplate.opsForZSet().incrementScore(monthKey, scoreKey, 1);
    }
}
