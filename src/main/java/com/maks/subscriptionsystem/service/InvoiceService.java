package com.maks.subscriptionsystem.service;

import com.maks.subscriptionsystem.dto.InvoiceDto;
import com.maks.subscriptionsystem.entity.Invoice;
import com.maks.subscriptionsystem.entity.Payment;
import com.maks.subscriptionsystem.entity.Subscription;
import com.maks.subscriptionsystem.exception.ConflictException;
import com.maks.subscriptionsystem.exception.ItemNotFoundException;
import com.maks.subscriptionsystem.mapper.InvoiceMapper;
import com.maks.subscriptionsystem.repository.InvoiceRepository;
import com.maks.subscriptionsystem.repository.PaymentRepository;
import com.maks.subscriptionsystem.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static com.maks.subscriptionsystem.entity.Invoice.InvoiceStatus.*;
import static com.maks.subscriptionsystem.entity.Subscription.SubscriptionStatus.ACTIVE;

@RequiredArgsConstructor
@Service
public class InvoiceService {
    private final InvoiceRepository invoiceRepository;
    private final PaymentRepository paymentRepository;
    private final SubscriptionRepository subscriptionRepository;

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
        invoice.setDueDate(LocalDateTime.now().plusMinutes(5));
        invoice.setStatus(PENDING);
        invoiceRepository.save(invoice);
    }

    @Transactional
    public void payInvoice(Long invoiceId) {
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new ItemNotFoundException("Invoice not found with id: " + invoiceId));

        if (invoice.getStatus() != PENDING)
            throw new ConflictException(
                    "Invoice with ID " + invoiceId + " cannot be paid in status: " + invoice.getStatus()
            );

        invoice.setStatus(PAID);

        LocalDateTime now = LocalDateTime.now();

        Subscription subscription = invoice.getSubscription();
        subscription.setStartDate(now);
        subscription.setEndDate(now.plusDays(subscription.getPlan().getDurationDays()));
        subscription.setStatus(ACTIVE);

        Payment payment = new Payment();
        payment.setInvoice(invoice);
        payment.setAmount(invoice.getAmount());
        payment.setPaidAt(now);

        paymentRepository.save(payment);
        invoiceRepository.save(invoice);
        subscriptionRepository.save(subscription);
    }
}
