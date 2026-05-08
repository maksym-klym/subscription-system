package com.maks.subscriptionsystem.service;

import com.maks.subscriptionsystem.dto.InvoiceDto;
import com.maks.subscriptionsystem.entity.Invoice;
import com.maks.subscriptionsystem.entity.Subscription;
import com.maks.subscriptionsystem.exception.ItemNotFoundException;
import com.maks.subscriptionsystem.mapper.InvoiceMapper;
import com.maks.subscriptionsystem.repository.InvoiceRepository;
import com.maks.subscriptionsystem.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class InvoiceService {
    private final InvoiceRepository invoiceRepository;
    private final SubscriptionRepository subscriptionRepository;

    public InvoiceDto get(Long invoiceId) {
        Invoice invoice = invoiceRepository.findById(invoiceId).orElseThrow(() -> new ItemNotFoundException("Invoice not found with id: " + invoiceId));
        return InvoiceMapper.toDto(invoice);
    }

    public List<InvoiceDto> getAll() { return invoiceRepository.findAll().stream().map(InvoiceMapper::toDto).toList(); }

    public List<InvoiceDto> getAllBySubscriptionId(Long subscriptionId) {
        return invoiceRepository.findAllBySubscriptionId(subscriptionId).stream().map(InvoiceMapper::toDto).toList();
    }

    public void generateInvoice(Subscription subscription) {
        Invoice invoice = new Invoice();
        invoice.setSubscription(subscription);
        invoice.setAmount(subscription.getPlan().getPrice());
        invoice.setDueDate(subscription.getStartDate().plusDays(5));
        invoice.setStatus(Invoice.InvoiceStatus.CREATED);
        invoiceRepository.save(invoice);
    }
}
