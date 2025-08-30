package com.timetoast.statistics_service.member.application;

import com.timetoast.statistics_service.member.domain.dto.SignUpInfo;
import com.timetoast.statistics_service.member.domain.enums.MemberRole;
import com.timetoast.statistics_service.member.application.port.in.MemberJoinUseCase;
import com.timetoast.statistics_service.member.application.port.out.MemberJoinPort;
import com.timetoast.statistics_service.member.domain.dto.MemberJoinDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MemberJoinService implements MemberJoinUseCase {
    private final MemberJoinPort memberJoinPort;

    public MemberJoinService(final MemberJoinPort memberJoinPort) {
        this.memberJoinPort = memberJoinPort;
    }

    @Override
    public void saveSignUpState(MemberJoinDto dto) {
        memberJoinPort.updateTotalSignUpState(dto);
        memberJoinPort.updateMonthlySignUpState(dto);

        log.info("saved joined member stats id={}", dto.memberId());
    }

    @Override
    public SignUpInfo getSignUpInfo() {

        log.info("success get total signUpInfo");

        return SignUpInfo.builder()
                .totalUserCount(memberJoinPort.getTotalSignUpState(MemberRole.USER))
                .totalCreatorCount(memberJoinPort.getTotalSignUpState(MemberRole.CREATOR))
                .build();
    }
}
