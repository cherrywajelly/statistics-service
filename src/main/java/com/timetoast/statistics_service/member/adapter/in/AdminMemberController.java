package com.timetoast.statistics_service.member.adapter.in;

import com.timetoast.statistics_service.global.annotation.Login;
import com.timetoast.statistics_service.global.dto.LoginMember;
import com.timetoast.statistics_service.member.domain.dto.SignUpInfo;
import com.timetoast.statistics_service.member.port.in.MemberJoinUseCase;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/statistics/api/v3/members")
public class AdminMemberController {

    private final MemberJoinUseCase memberJoinUseCase;

    public AdminMemberController(final MemberJoinUseCase memberJoinUseCase) {
        this.memberJoinUseCase = memberJoinUseCase;
    }

    @GetMapping("/signupInfo")
    public SignUpInfo getSignUpInfo(@Login LoginMember loginMember) {
        return memberJoinUseCase.getSignUpInfo(loginMember);
    }
}
