package com.timetoast.statistics_service.member.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.timetoast.statistics_service.member.domain.model.MemberRole;

import java.time.LocalDate;

public record MemberJoinDto(
        long memberId,
        MemberRole memberRole,
        String nickname,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        LocalDate joinDate
) {
}
