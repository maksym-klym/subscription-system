package com.maks.subscriptionsystem.controller;

import com.maks.subscriptionsystem.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/{invoiceId}")
    public void payInvoiceById(@PathVariable Long invoiceId) { paymentService.payInvoice(invoiceId); }
}
