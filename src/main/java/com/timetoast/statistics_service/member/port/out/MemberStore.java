package com.timetoast.statistics_service.member.port.out;

import com.timetoast.statistics_service.member.domain.dto.MemberJoinDto;

public interface MemberStore {
    void saveJoinedMemberStats(MemberJoinDto memberJoinDto);
}
