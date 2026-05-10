package com.maks.subscriptionsystem.mapper;

import com.maks.subscriptionsystem.dto.PlanDto;
import com.maks.subscriptionsystem.entity.Plan;

public class PlanMapper {
    public static PlanDto toDto(Plan plan) {
        PlanDto planDto = new PlanDto();
        planDto.setId(plan.getId());
        planDto.setDurationDays(plan.getDurationDays());
        planDto.setPrice(plan.getPrice());
        planDto.setName(plan.getName());
        return planDto;
    }
}
