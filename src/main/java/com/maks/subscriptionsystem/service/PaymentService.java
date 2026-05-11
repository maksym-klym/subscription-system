package com.maks.subscriptionsystem.service;

import com.maks.subscriptionsystem.entity.Invoice;
import com.maks.subscriptionsystem.entity.Payment;
import com.maks.subscriptionsystem.entity.Subscription;
import com.maks.subscriptionsystem.exception.InvoiceAlreadyPaidException;
import com.maks.subscriptionsystem.exception.ItemNotFoundException;
import com.maks.subscriptionsystem.repository.InvoiceRepository;
import com.maks.subscriptionsystem.repository.PaymentRepository;
import jakarta.transaction.Transactional;
import lombok.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final InvoiceRepository invoiceRepository;

    @Transactional
    public void payInvoice(Long invoiceId) {
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new ItemNotFoundException("Invoice not found with id: " + invoiceId));

        if(invoice.getStatus() == Invoice.InvoiceStatus.PAID)
            throw new InvoiceAlreadyPaidException(invoiceId);

        Payment payment = new Payment();
        payment.setInvoice(invoice);
        payment.setAmount(invoice.getAmount());
        payment.setPaidAt(LocalDateTime.now());
        invoice.setStatus(Invoice.InvoiceStatus.PAID);
        invoiceRepository.save(invoice);
        paymentRepository.save(payment);
    }
}
