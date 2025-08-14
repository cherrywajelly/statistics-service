package com.timetoast.statistics_service.member.port.in;

import com.timetoast.statistics_service.global.dto.LoginMember;
import com.timetoast.statistics_service.member.domain.dto.MemberJoinDto;
import com.timetoast.statistics_service.member.domain.dto.SignUpInfo;

public interface MemberJoinUseCase {
    void saveSignUpState(MemberJoinDto dto);
    SignUpInfo getSignUpInfo(LoginMember loginMember);
}
