package com.maks.subscriptionsystem.service;

import com.maks.subscriptionsystem.dto.PlanDto;
import com.maks.subscriptionsystem.entity.Plan;
import com.maks.subscriptionsystem.exception.ItemNotFoundException;
import com.maks.subscriptionsystem.mapper.PlanMapper;
import com.maks.subscriptionsystem.repository.PlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PlanService {
    private final PlanRepository planRepository;

    public Page<PlanDto> getAll(Plan.PlanName planName, Pageable pageable) {
        return planRepository.findAllBy(planName, pageable).map(PlanMapper::toDto);
    }

    public PlanDto getPlanById(Long id) {
        Plan plan = planRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("Plan not found with id: " + id));
        return PlanMapper.toDto(plan);
    }
}
