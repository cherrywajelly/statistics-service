package com.timetoast.statistics_service.member.adapter.out;

import com.timetoast.statistics_service.member.domain.dto.MemberJoinDto;
import com.timetoast.statistics_service.member.domain.enums.MemberRole;
import com.timetoast.statistics_service.util.TestSupport;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.LocalDate;
import java.time.YearMonth;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class MemberRedisAdapterTestSupport extends TestSupport {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private MemberRedisAdapter memberRedisAdapter;

    private static MemberJoinDto userMemberDto;

    @BeforeAll
    static void setUp(){
        userMemberDto = new MemberJoinDto(1L, MemberRole.USER, "nickanme", LocalDate.now());
    }

    @Test
    @DisplayName("User 총 집계 데이터 초기 조회 및 갱신 후 조회 테스트")
    public void updateTotalSignUpState() {
        //given when
        long initUserTotalNum = memberRedisAdapter.getTotalSignUpState(userMemberDto.memberRole());

        //then
        assertThat(initUserTotalNum).isEqualTo(0);

        //given
        long beforeUserTotalNum = memberRedisAdapter.getTotalSignUpState(userMemberDto.memberRole());

        //when
        memberRedisAdapter.updateTotalSignUpState(userMemberDto);
        long userTotalNum = memberRedisAdapter.getTotalSignUpState(userMemberDto.memberRole());

        //then
        assertThat(userTotalNum).isEqualTo(beforeUserTotalNum+1);

    }

    @Test
    @DisplayName("User 월별 집계 데이터 초기 조회 및 갱신 후 조회 테스트")
    public void updateMonthlySignUpState() {
        //given when
        YearMonth yearMonth = YearMonth.of(userMemberDto.joinDate().getYear(), userMemberDto.joinDate().getMonth());
        long initMonthlySignUpNum = memberRedisAdapter.getMonthlySignUpState(userMemberDto.memberRole(), yearMonth);

        //then
        assertThat(initMonthlySignUpNum).isEqualTo(0);

        //given
        long beforeUserMonthlyNum = memberRedisAdapter.getTotalSignUpState(userMemberDto.memberRole());

        //when
        memberRedisAdapter.updateMonthlySignUpState(userMemberDto);
        long userMonthlyNum = memberRedisAdapter.getMonthlySignUpState(userMemberDto.memberRole(),yearMonth);

        //then
        assertThat(userMonthlyNum).isEqualTo(beforeUserMonthlyNum+1);
    }

}