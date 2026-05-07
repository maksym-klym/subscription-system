package com.maks.subscriptionsystem.controller;

import com.maks.subscriptionsystem.dto.SubscriptionDto;
import com.maks.subscriptionsystem.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/subscriptions")
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    @GetMapping
    public List<SubscriptionDto> getAll() { return subscriptionService.getAll(); }

    @GetMapping("/{id}")
    public SubscriptionDto get(@PathVariable Long id) { return subscriptionService.get(id); }

    @PostMapping
    public SubscriptionDto create(@RequestParam Long userId, @RequestParam Long planId) { return subscriptionService.createSubscription(userId, planId); }

    @PutMapping("/{id}/cancel")
    public SubscriptionDto cancelSubscription(@PathVariable Long id) { return subscriptionService.cancelSubscription(id); }
}
