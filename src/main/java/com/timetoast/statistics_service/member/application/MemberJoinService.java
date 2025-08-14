package com.timetoast.statistics_service.member.application;

import com.timetoast.statistics_service.global.dto.LoginMember;
import com.timetoast.statistics_service.member.domain.dto.SignUpInfo;
import com.timetoast.statistics_service.member.domain.model.MemberRole;
import com.timetoast.statistics_service.member.port.in.MemberJoinUseCase;
import com.timetoast.statistics_service.member.port.out.MemberStore;
import com.timetoast.statistics_service.member.domain.dto.MemberJoinDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class MemberJoinService implements MemberJoinUseCase {
    private final MemberStore memberStore;

    public MemberJoinService(final MemberStore memberStore) {
        this.memberStore = memberStore;
    }

    @Override
    public void saveSignUpState(MemberJoinDto dto) {
        memberStore.updateTotalSignUpState(dto);
        memberStore.updateMonthlySignUpState(dto);

        log.info("saved joined member stats id={}", dto.memberId());
    }

    @Override
    public SignUpInfo getSignUpInfo(LoginMember loginMember) {

        log.info("success get total signUpInfo");

        return SignUpInfo.builder()
                .totalUserCount(memberStore.getTotalSignUpState(MemberRole.USER))
                .totalCreatorCount(memberStore.getTotalSignUpState(MemberRole.CREATOR))
                .build();
    }
}
