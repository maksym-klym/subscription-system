package com.maks.subscriptionsystem.service;

import com.maks.subscriptionsystem.entity.Invoice;
import com.maks.subscriptionsystem.entity.Subscription;
import com.maks.subscriptionsystem.repository.InvoiceRepository;
import com.maks.subscriptionsystem.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static com.maks.subscriptionsystem.entity.Invoice.InvoiceStatus.FAILED;
import static com.maks.subscriptionsystem.entity.Invoice.InvoiceStatus.PENDING;
import static com.maks.subscriptionsystem.entity.Subscription.SubscriptionStatus.CANCELED;

@Service
@RequiredArgsConstructor
public class InvoiceSchedulerService {
    private final InvoiceRepository invoiceRepository;
    private final SubscriptionRepository subscriptionRepository;

    @Scheduled(fixedRate = 60000)
    @Transactional
    public void handleOverdueInvoices() {
        List<Invoice> overdueInvoices = invoiceRepository.findAllByStatusAndDueDateBefore(
                PENDING,
                LocalDateTime.now()
            );

        for (Invoice invoice : overdueInvoices) {
            invoice.setStatus(FAILED);

            Subscription subscription = invoice.getSubscription();
            subscription.setStatus(CANCELED);

            invoiceRepository.save(invoice);
            subscriptionRepository.save(subscription);
        }
    }
}