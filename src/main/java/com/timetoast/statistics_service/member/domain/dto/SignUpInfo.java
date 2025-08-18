package com.timetoast.statistics_service.member.domain.dto;

import lombok.Builder;

@Builder
public record SignUpInfo(
        long totalUserCount,
        long totalCreatorCount
) {
}
