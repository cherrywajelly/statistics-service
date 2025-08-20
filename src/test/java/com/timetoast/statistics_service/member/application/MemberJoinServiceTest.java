package com.timetoast.statistics_service.member.application;

import com.timetoast.statistics_service.member.application.port.out.MemberStore;
import com.timetoast.statistics_service.member.domain.dto.MemberJoinDto;
import com.timetoast.statistics_service.member.domain.model.MemberRole;
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
    private MemberStore memberStore;

    @InjectMocks
    private MemberJoinService memberJoinService;


    @Test
    void saveSignUpState() {
        //given
        MemberJoinDto memberJoinDto = new MemberJoinDto(1L, MemberRole.USER, "nickname", LocalDate.now());

        //when
        memberJoinService.saveSignUpState(memberJoinDto);

        //then
        verify(memberStore).updateTotalSignUpState(memberJoinDto);
        verify(memberStore).updateMonthlySignUpState(memberJoinDto);
    }

    @Test
    void getSignUpInfo() {
        //given

        //when
        memberJoinService.getSignUpInfo();

        //then
        verify(memberStore).getTotalSignUpState(MemberRole.USER);
        verify(memberStore).getTotalSignUpState(MemberRole.CREATOR);
    }
}