package com.timetoast.statistics_service.member.domain.dto;

import com.timetoast.statistics_service.member.domain.model.MemberRole;

import java.time.LocalDate;

public record MemberJoinDto(
        long memberId,
        MemberRole memberRole,
        String nickname,
        LocalDate joinDate
) {
}
