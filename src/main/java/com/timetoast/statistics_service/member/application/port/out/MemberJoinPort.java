package com.timetoast.statistics_service.member.application.port.out;

import com.timetoast.statistics_service.member.domain.dto.MemberJoinDto;
import com.timetoast.statistics_service.member.domain.enums.MemberRole;

import java.time.YearMonth;

public interface MemberJoinPort {
    void updateTotalSignUpState(MemberJoinDto memberJoinDto);
    void updateMonthlySignUpState(MemberJoinDto memberJoinDto);
    long getTotalSignUpState(MemberRole memberRole);
    long getMonthlySignUpState(MemberRole memberRole, YearMonth yearMonth);
}
