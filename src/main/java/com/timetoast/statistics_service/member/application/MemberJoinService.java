package com.timetoast.statistics_service.member.application;

import com.timetoast.statistics_service.member.port.in.MemberJoinUseCase;
import com.timetoast.statistics_service.member.port.out.MemberStore;
import com.timetoast.statistics_service.member.domain.dto.MemberJoinDto;
import org.springframework.stereotype.Service;

@Service
public class MemberJoinService implements MemberJoinUseCase {
    private final MemberStore memberStore;

    public MemberJoinService(final MemberStore memberStore) {
        this.memberStore = memberStore;
    }

    @Override
    public void saveJoinedMemberStats(MemberJoinDto dto) {
        memberStore.saveJoinedMemberStats(dto);
    }
}
