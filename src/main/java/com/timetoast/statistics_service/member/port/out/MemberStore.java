package com.timetoast.statistics_service.member.port.out;

import com.timetoast.statistics_service.member.domain.dto.MemberJoinDto;
import com.timetoast.statistics_service.member.domain.model.MemberRole;

public interface MemberStore {
    void updateTotalSignUpState(MemberJoinDto memberJoinDto);
    void updateMonthlySignUpState(MemberJoinDto memberJoinDto);
    long getTotalSignUpState(MemberRole memberRole);
}
