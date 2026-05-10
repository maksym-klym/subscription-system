package com.maks.subscriptionsystem.mapper;

import com.maks.subscriptionsystem.dto.InvoiceDto;
import com.maks.subscriptionsystem.entity.Invoice;

public class InvoiceMapper {
    public static InvoiceDto toDto(Invoice invoice) {
        InvoiceDto invoiceDto = new InvoiceDto();
        invoiceDto.setId(invoice.getId());
        invoiceDto.setSubscriptionId(invoice.getSubscription().getId());
        invoiceDto.setAmount(invoice.getAmount());
        invoiceDto.setDueDate(invoice.getDueDate());
        invoiceDto.setStatus(invoice.getStatus());
        return invoiceDto;
    }
}
