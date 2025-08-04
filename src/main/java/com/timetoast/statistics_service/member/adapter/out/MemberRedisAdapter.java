package com.timetoast.statistics_service.member.adapter.out;

import com.timetoast.statistics_service.member.domain.dto.MemberJoinDto;
import com.timetoast.statistics_service.member.port.out.MemberStore;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.format.DateTimeFormatter;

@Repository
public class MemberRedisAdapter implements MemberStore {

    private final RedisTemplate<String, String> redisTemplate;

    public MemberRedisAdapter(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void saveJoinedMemberStats(MemberJoinDto memberJoinDto) {
        String monthKey = memberJoinDto.memberRole() +":month-signup:"
                + memberJoinDto.joinDate().format(DateTimeFormatter.ofPattern("yyyy-MM"));

        String scoreKey = String.valueOf(memberJoinDto.joinDate().getDayOfMonth());

        redisTemplate.opsForZSet().incrementScore(monthKey, scoreKey, 1);
    }
}
