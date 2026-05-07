package com.maks.subscriptionsystem.service;

import com.maks.subscriptionsystem.dto.PlanDto;
import com.maks.subscriptionsystem.entity.Plan;
import com.maks.subscriptionsystem.exception.ItemNotFoundException;
import com.maks.subscriptionsystem.mapper.PlanMapper;
import com.maks.subscriptionsystem.repository.PlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PlanService {
    private final PlanRepository planRepository;

    public List<PlanDto> getAll() { return planRepository.findAll().stream().map(PlanMapper::toDto).toList(); }

    public PlanDto getPlanById(Long id) {
        Plan plan = planRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("Plan not found with id: " + id));
        return PlanMapper.toDto(plan);
    }
}
