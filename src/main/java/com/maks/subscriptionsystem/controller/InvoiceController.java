package com.maks.subscriptionsystem.controller;

import com.maks.subscriptionsystem.dto.InvoiceDto;
import com.maks.subscriptionsystem.service.InvoiceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Invoices", description = "Invoice management APIs")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {
    private final InvoiceService invoiceService;

    @Operation(summary = "Get all invoices")
    @GetMapping
    public List<InvoiceDto> getAll() { return invoiceService.getAll(); }

    @Operation(summary = "Get invoice by ID")
    @GetMapping("/{id}")
    public InvoiceDto get(@Parameter(description = "Invoice ID") @PathVariable Long id){ return invoiceService.get(id); }
}
