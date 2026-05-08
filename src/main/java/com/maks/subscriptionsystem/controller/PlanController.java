package com.maks.subscriptionsystem.controller;

import com.maks.subscriptionsystem.dto.PlanDto;
import com.maks.subscriptionsystem.service.PlanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Plans", description = "Plan management APIs")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/plans")
public class PlanController {
    private final PlanService planService;

    @Operation(summary = "Get all plans")
    @GetMapping
    public List<PlanDto> getAll() { return planService.getAll(); }

    @Operation(summary = "Get plan by ID")
    @GetMapping("/{id}")
    public PlanDto get(@Parameter(description = "Plan ID") @PathVariable Long id) { return planService.getPlanById(id); }
}
