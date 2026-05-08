package com.maks.subscriptionsystem.controller;

import com.maks.subscriptionsystem.dto.InvoiceDto;
import com.maks.subscriptionsystem.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {
    private final InvoiceService invoiceService;

    @GetMapping
    public List<InvoiceDto> getAll() { return invoiceService.getAll(); }

    @GetMapping("/{id}")
    public InvoiceDto get(@PathVariable Long id){ return invoiceService.get(id); }
}
