package com.maks.subscriptionsystem.controller;

import com.maks.subscriptionsystem.dto.PlanDto;
import com.maks.subscriptionsystem.service.PlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/plans")
public class PlanController {
    private final PlanService planService;

    @GetMapping
    public List<PlanDto> getAll() { return planService.getAll(); }

    @GetMapping("/{id}")
    public PlanDto get(@PathVariable Long id) { return planService.getPlanById(id); }
}
