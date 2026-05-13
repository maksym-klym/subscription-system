package com.maks.subscriptionsystem.controller;

import com.maks.subscriptionsystem.dto.PaymentDto;
import com.maks.subscriptionsystem.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Payments", description = "Payment management APIs")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    private final PaymentService paymentService;

    @Operation(summary = "Get payment by ID")
    @GetMapping("/{id}")
    public PaymentDto get(@Parameter(description = "Payment ID") @PathVariable Long id) { return paymentService.get(id); }

    @Operation(summary = "Get all payments")
    @GetMapping
    public List<PaymentDto> getAll() { return paymentService.getAll(); }
}
