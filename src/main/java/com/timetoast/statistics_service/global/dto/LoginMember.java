package com.timetoast.statistics_service.global.dto;


import com.timetoast.statistics_service.member.domain.model.MemberRole;
import lombok.Builder;

@Builder
public record LoginMember(
        long id,
        MemberRole role
) {

}
