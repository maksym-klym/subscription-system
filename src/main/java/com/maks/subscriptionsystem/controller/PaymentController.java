package com.maks.subscriptionsystem.controller;

import com.maks.subscriptionsystem.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Payments", description = "Payment management APIs")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    private final PaymentService paymentService;

    @Operation(summary = "Pay invoice by ID")
    @PostMapping("/{invoiceId}")
    public void payInvoiceById(@Parameter(description = "Invoice ID") @PathVariable Long invoiceId) {
        paymentService.payInvoice(invoiceId);
    }
}
