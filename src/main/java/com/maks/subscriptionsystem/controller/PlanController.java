package com.maks.subscriptionsystem.controller;

import com.maks.subscriptionsystem.dto.PlanDto;
import com.maks.subscriptionsystem.service.PlanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Plans", description = "Plan management APIs")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/plans")
public class PlanController {
    private final PlanService planService;

    @Operation(summary = "Get all plans")
    @GetMapping
    public Page<PlanDto> getAll(@ParameterObject @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) { return planService.getAll(pageable); }

    @Operation(summary = "Get plan by ID")
    @GetMapping("/{id}")
    public PlanDto get(@Parameter(description = "Plan ID") @PathVariable Long id) { return planService.getPlanById(id); }
}
