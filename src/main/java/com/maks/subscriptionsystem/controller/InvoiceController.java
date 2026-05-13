package com.maks.subscriptionsystem.controller;

import com.maks.subscriptionsystem.dto.InvoiceDto;
import com.maks.subscriptionsystem.entity.Invoice;
import com.maks.subscriptionsystem.service.InvoiceService;
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

@Tag(name = "Invoices", description = "Invoice management APIs")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {
    private final InvoiceService invoiceService;

    @Operation(summary = "Get invoice by ID")
    @GetMapping("/{id}")
    public InvoiceDto get(@Parameter(description = "Invoice ID") @PathVariable Long id) { return invoiceService.get(id); }

    @Operation(summary = "Get all invoices")
    @GetMapping
    public Page<InvoiceDto> getAll(
            @RequestParam(required = false) @Parameter(description = "Invoice status") Invoice.InvoiceStatus status,
            @PageableDefault(sort = "id", direction = Sort.Direction.ASC) @ParameterObject Pageable pageable) {
        return invoiceService.getAll(status, pageable);
    }

    @Operation(summary = "Pay invoice by ID")
    @PostMapping("/{invoiceId}/pay")
    public void payInvoiceById(@Parameter(description = "Invoice ID") @PathVariable Long invoiceId) {
        invoiceService.payInvoice(invoiceId);
    }
}
