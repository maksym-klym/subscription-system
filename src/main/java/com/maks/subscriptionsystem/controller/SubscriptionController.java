package com.maks.subscriptionsystem.controller;

import com.maks.subscriptionsystem.dto.InvoiceDto;
import com.maks.subscriptionsystem.dto.SubscriptionDto;
import com.maks.subscriptionsystem.dto.filter.SubscriptionFilter;
import com.maks.subscriptionsystem.service.InvoiceService;
import com.maks.subscriptionsystem.service.SubscriptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Subscriptions", description = "Subscription management APIs")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/subscriptions")
public class SubscriptionController {
    private final SubscriptionService subscriptionService;
    private final InvoiceService invoiceService;

    @Operation(summary = "Create subscription with user ID and plan ID")
    @PostMapping
    public SubscriptionDto create(
            @Parameter(description = "User ID") @RequestParam Long userId,
            @Parameter(description = "Plan ID") @RequestParam Long planId) {
        return subscriptionService.createSubscription(userId, planId);
    }

    @Operation(summary = "Get subscription by ID")
    @GetMapping("/{id}")
    public SubscriptionDto get(@Parameter(description = "Subscription ID") @PathVariable Long id) { return subscriptionService.get(id); }

    @Operation(summary = "Get all subscriptions")
    @GetMapping
    public Page<SubscriptionDto> getAll(
            @ParameterObject SubscriptionFilter subscriptionFilter,
            @PageableDefault(sort = "id", direction = Sort.Direction.ASC) @ParameterObject Pageable pageable) {
        return subscriptionService.getAll(subscriptionFilter, pageable);
    }

    @Operation(summary = "Get all invoices by subscription ID")
    @GetMapping("/{id}/invoices")
    public Page<InvoiceDto> getAllInvoicesById(
            @Parameter(description = "Subscription ID") @PathVariable Long id,
            @PageableDefault(sort = "id", direction = Sort.Direction.ASC) @ParameterObject Pageable pageable) {
        return invoiceService.getAllBySubscriptionId(id, pageable);
    }

    @Operation(summary = "Cancel subscription by ID")
    @PutMapping("/{id}/cancel")
    public SubscriptionDto cancelSubscription(
            @Parameter(description = "Subscription ID") @PathVariable Long id) {
        return subscriptionService.cancelSubscription(id);
    }
}
