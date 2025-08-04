package com.timetoast.statistics_service.member.port.in;

import com.timetoast.statistics_service.member.domain.dto.MemberJoinDto;

public interface MemberJoinUseCase {
    void saveJoinedMemberStats(MemberJoinDto dto);
}
