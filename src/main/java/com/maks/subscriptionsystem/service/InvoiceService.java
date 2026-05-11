package com.maks.subscriptionsystem.service;

import com.maks.subscriptionsystem.dto.InvoiceDto;
import com.maks.subscriptionsystem.entity.Invoice;
import com.maks.subscriptionsystem.entity.Payment;
import com.maks.subscriptionsystem.entity.Subscription;
import com.maks.subscriptionsystem.exception.InvoiceAlreadyPaidException;
import com.maks.subscriptionsystem.exception.ItemNotFoundException;
import com.maks.subscriptionsystem.mapper.InvoiceMapper;
import com.maks.subscriptionsystem.repository.InvoiceRepository;
import com.maks.subscriptionsystem.repository.PaymentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class InvoiceService {
    private final InvoiceRepository invoiceRepository;
    private final PaymentRepository paymentRepository;

    public InvoiceDto get(Long invoiceId) {
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new ItemNotFoundException("Invoice not found with id: " + invoiceId));
        return InvoiceMapper.toDto(invoice);
    }

    public Page<InvoiceDto> getAll(Invoice.InvoiceStatus status, Pageable pageable) {
        return invoiceRepository.findAllBy(status, pageable).map(InvoiceMapper::toDto);
    }

    public Page<InvoiceDto> getAllBySubscriptionId(Long subscriptionId, Pageable pageable) {
        return invoiceRepository.findAllBySubscriptionId(subscriptionId, pageable).map(InvoiceMapper::toDto);
    }

    public void generateInvoice(Subscription subscription) {
        Invoice invoice = new Invoice();
        invoice.setSubscription(subscription);
        invoice.setAmount(subscription.getPlan().getPrice());
        invoice.setDueDate(subscription.getStartDate().plusDays(5));
        invoice.setStatus(Invoice.InvoiceStatus.CREATED);
        invoiceRepository.save(invoice);
    }

    @Transactional
    public void payInvoice(Long invoiceId) {
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new ItemNotFoundException("Invoice not found with id: " + invoiceId));

        if(invoice.getStatus() == Invoice.InvoiceStatus.PAID)
            throw new InvoiceAlreadyPaidException(invoiceId);

        invoice.setStatus(Invoice.InvoiceStatus.PAID);
        Payment payment = new Payment();
        payment.setInvoice(invoice);
        payment.setAmount(invoice.getAmount());
        payment.setPaidAt(LocalDateTime.now());
        paymentRepository.save(payment);
        invoiceRepository.save(invoice);
    }
}
