package com.maks.subscriptionsystem.controller;

import com.maks.subscriptionsystem.dto.InvoiceDto;
import com.maks.subscriptionsystem.dto.SubscriptionDto;
import com.maks.subscriptionsystem.service.InvoiceService;
import com.maks.subscriptionsystem.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/subscriptions")
public class SubscriptionController {
    private final SubscriptionService subscriptionService;
    private final InvoiceService invoiceService;

    @GetMapping
    public List<SubscriptionDto> getAll() { return subscriptionService.getAll(); }

    @GetMapping("/{id}/invoices")
    public List<InvoiceDto> getAllInvoicesById(@PathVariable Long id) { return invoiceService.getAllBySubscriptionId(id); }

    @GetMapping("/{id}")
    public SubscriptionDto get(@PathVariable Long id) { return subscriptionService.get(id); }

    @PostMapping
    public SubscriptionDto create(@RequestParam Long userId, @RequestParam Long planId) { return subscriptionService.createSubscription(userId, planId); }

    @PutMapping("/{id}/cancel")
    public SubscriptionDto cancelSubscription(@PathVariable Long id) { return subscriptionService.cancelSubscription(id); }
}
