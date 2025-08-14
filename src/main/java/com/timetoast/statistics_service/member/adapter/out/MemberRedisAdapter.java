package com.timetoast.statistics_service.member.adapter.out;

import com.timetoast.statistics_service.member.domain.dto.MemberJoinDto;
import com.timetoast.statistics_service.member.domain.model.MemberRole;
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
    public void updateTotalSignUpState(MemberJoinDto memberJoinDto) {
        String totalKey = SIGN_UP.value()+COLON.value()+ memberJoinDto.memberRole();
        redisTemplate.opsForValue().increment(totalKey);
    }

    @Override
    public void updateMonthlySignUpState(MemberJoinDto memberJoinDto) {
        String monthKey = memberJoinDto.memberRole() + COLON.value() + MONTH_SIGNUP.value() + COLON.value()
                + memberJoinDto.joinDate().format(DateTimeFormatter.ofPattern("yyyy-MM"));

        String scoreKey = DAY.value()+COLON+ memberJoinDto.joinDate().getDayOfMonth();

        redisTemplate.opsForZSet().incrementScore(monthKey, scoreKey, 1);
    }

    @Override
    public long getTotalSignUpState(MemberRole memberRole) {
        try {
            return Long.parseLong(redisTemplate.opsForValue().get(SIGN_UP.value()+COLON.value()+memberRole));
        }catch (NumberFormatException e) {
            return 0L;
        }

    }

}
