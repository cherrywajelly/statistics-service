package com.timetoast.statistics_service.member.application;

import com.timetoast.statistics_service.member.application.port.out.MemberJoinPort;
import com.timetoast.statistics_service.member.domain.dto.MemberJoinDto;
import com.timetoast.statistics_service.member.domain.enums.MemberRole;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MemberJoinServiceTest {

    @Mock
    private MemberJoinPort memberJoinPort;

    @InjectMocks
    private MemberJoinService memberJoinService;


    @Test
    void saveSignUpState() {
        //given
        MemberJoinDto memberJoinDto = new MemberJoinDto(1L, MemberRole.USER, "nickname", LocalDate.now());

        //when
        memberJoinService.saveSignUpState(memberJoinDto);

        //then
        verify(memberJoinPort).updateTotalSignUpState(memberJoinDto);
        verify(memberJoinPort).updateMonthlySignUpState(memberJoinDto);
    }

    @Test
    void getSignUpInfo() {
        //given

        //when
        memberJoinService.getSignUpInfo();

        //then
        verify(memberJoinPort).getTotalSignUpState(MemberRole.USER);
        verify(memberJoinPort).getTotalSignUpState(MemberRole.CREATOR);
    }
}