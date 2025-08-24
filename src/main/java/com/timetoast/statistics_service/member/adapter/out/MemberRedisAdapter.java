package com.timetoast.statistics_service.member.adapter.out;

import com.timetoast.statistics_service.member.domain.dto.MemberJoinDto;
import com.timetoast.statistics_service.member.domain.enums.MemberRole;
import com.timetoast.statistics_service.member.application.port.out.MemberJoinPort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Repository;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Set;

import static com.timetoast.statistics_service.global.constant.RedisKeyConstant.*;

@Repository
public class MemberRedisAdapter implements MemberJoinPort {

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

    @Override
    public long getMonthlySignUpState(MemberRole memberRole, YearMonth yearMonth) {
        String monthKey = memberRole + COLON.value() + MONTH_SIGNUP.value() + COLON.value()
                + yearMonth.format(DateTimeFormatter.ofPattern("yyyy-MM"));

        Set<ZSetOperations.TypedTuple<String>> dayStats =
                redisTemplate.opsForZSet().rangeWithScores(monthKey, 0, -1);

        if (dayStats == null) {
            return 0L;
        }

        return dayStats.stream()
                .mapToLong(tuple -> tuple.getScore() == null ? 0L : tuple.getScore().longValue())
                .sum();

    }

}
