package com.maks.subscriptionsystem.service;

import com.maks.subscriptionsystem.dto.InvoiceDto;
import com.maks.subscriptionsystem.entity.Invoice;
import com.maks.subscriptionsystem.entity.Payment;
import com.maks.subscriptionsystem.entity.Plan;
import com.maks.subscriptionsystem.entity.Subscription;
import com.maks.subscriptionsystem.exception.ConflictException;
import com.maks.subscriptionsystem.exception.ItemNotFoundException;
import com.maks.subscriptionsystem.repository.InvoiceRepository;
import com.maks.subscriptionsystem.repository.PaymentRepository;
import com.maks.subscriptionsystem.repository.SubscriptionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class InvoiceServiceTest {
    @Mock
    private InvoiceRepository invoiceRepository;
    @Mock
    private PaymentRepository paymentRepository;
    @Mock
    private SubscriptionRepository subscriptionRepository;
    @InjectMocks
    private InvoiceService invoiceService;

    @Test
    void shouldReturnInvoiceById() {
        Subscription subscription = new Subscription();
        subscription.setId(1L);

        Invoice invoice = new Invoice();
        invoice.setId(1L);
        invoice.setSubscription(subscription);

        when(invoiceRepository.findById(1L)).thenReturn(Optional.of(invoice));

        InvoiceDto result = invoiceService.get(1L);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);

        verify(invoiceRepository).findById(1L);
    }

    @Test
    void shouldThrowWhenInvoiceNotFound() {
        when(invoiceRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> invoiceService.get(1L))
                .isInstanceOf(ItemNotFoundException.class)
                .hasMessage("Invoice not found with id: 1");

        verify(invoiceRepository).findById(1L);
    }

    @Test
    void shouldReturnAllInvoices() {
        Subscription subscription1 = new Subscription();
        subscription1.setId(1L);
        Subscription subscription2 = new Subscription();
        subscription2.setId(2L);

        Invoice invoice1 = new Invoice();
        invoice1.setId(1L);
        invoice1.setSubscription(subscription1);
        Invoice invoice2 = new Invoice();
        invoice2.setId(2L);
        invoice2.setSubscription(subscription2);

        Pageable pageable = PageRequest.of(0, 10);
        Page<Invoice> page = new PageImpl<>(List.of(invoice1, invoice2));

        when(invoiceRepository.findAllBy(Invoice.InvoiceStatus.PAID, pageable)).thenReturn(page);

        Page<InvoiceDto> result = invoiceService.getAll(Invoice.InvoiceStatus.PAID, pageable);

        assertThat(result.getContent()).hasSize(2);
        assertThat(result.getContent())
                .extracting(InvoiceDto::getId)
                .containsExactlyInAnyOrder(1L, 2L);

        verify(invoiceRepository).findAllBy(Invoice.InvoiceStatus.PAID, pageable);
    }

    @Test
    void shouldReturnAllInvoicesBySubscriptionId() {
        Subscription subscription = new Subscription();
        subscription.setId(1L);
        Long subscriptionId = subscription.getId();

        Invoice invoice1 = new Invoice();
        invoice1.setId(1L);
        invoice1.setSubscription(subscription);
        Invoice invoice2 = new Invoice();
        invoice2.setId(2L);
        invoice2.setSubscription(subscription);

        Pageable pageable = PageRequest.of(0, 10);
        Page<Invoice> page = new PageImpl<>(List.of(invoice1, invoice2));

        when(invoiceRepository.findAllBySubscriptionId(subscriptionId, pageable)).thenReturn(page);

        Page<InvoiceDto> result = invoiceService.getAllBySubscriptionId(subscriptionId, pageable);

        assertThat(result.getContent()).hasSize(2);
        assertThat(result.getContent())
                .extracting(InvoiceDto::getId)
                .containsExactlyInAnyOrder(1L, 2L);

        verify(invoiceRepository).findAllBySubscriptionId(subscriptionId, pageable);
    }

    @Test
    void shouldGenerateInvoice() {
        Plan plan = new Plan();
        plan.setPrice(BigDecimal.valueOf(29.99));

        Subscription subscription = new Subscription();
        subscription.setPlan(plan);

        ArgumentCaptor<Invoice> captor = ArgumentCaptor.forClass(Invoice.class);

        invoiceService.generateInvoice(subscription);

        verify(invoiceRepository).save(captor.capture());

        Invoice savedInvoice = captor.getValue();

        assertThat(savedInvoice.getSubscription()).isEqualTo(subscription);
        assertThat(savedInvoice.getAmount()).isEqualTo(BigDecimal.valueOf(29.99));
        assertThat(savedInvoice.getStatus()).isEqualTo(Invoice.InvoiceStatus.PENDING);
        assertThat(savedInvoice.getDueDate()).isNotNull();
    }

    @Test
    void shouldPayInvoice() {
        Plan plan = new Plan();
        plan.setDurationDays(7);
        plan.setId(1L);

        Subscription subscription = new Subscription();
        subscription.setId(1L);
        subscription.setPlan(plan);
        subscription.setStatus(Subscription.SubscriptionStatus.PENDING_PAYMENT);

        Invoice invoice = new Invoice();
        invoice.setId(1L);
        invoice.setStatus(Invoice.InvoiceStatus.PENDING);
        invoice.setAmount(subscription.getPlan().getPrice());
        invoice.setSubscription(subscription);

        when(invoiceRepository.findById(1L))
                .thenReturn(Optional.of(invoice));

        invoiceService.payInvoice(1L);

        assertThat(invoice.getStatus()).isEqualTo(Invoice.InvoiceStatus.PAID);

        assertThat(subscription.getStatus()).isEqualTo(Subscription.SubscriptionStatus.ACTIVE);
        assertThat(subscription.getStartDate()).isNotNull();
        assertThat(subscription.getEndDate()).isEqualTo(
                subscription.getStartDate().plusDays(plan.getDurationDays())
        );

        verify(paymentRepository).save(any(Payment.class));
        verify(invoiceRepository).save(invoice);
        verify(subscriptionRepository).save(subscription);
    }

    @Test
    void shouldThrowWhenPayingNonExistingInvoice() {
        when(invoiceRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> invoiceService.payInvoice(1L))
                .isInstanceOf(ItemNotFoundException.class)
                .hasMessage("Invoice not found with id: 1");

        verify(invoiceRepository).findById(1L);
    }

    @Test
    void shouldThrowWhenInvoiceAlreadyPaid() {
        Subscription subscription = new Subscription();
        subscription.setId(1L);

        Invoice invoice = new Invoice();
        invoice.setId(1L);
        invoice.setStatus(Invoice.InvoiceStatus.PAID);
        invoice.setSubscription(subscription);

        when(invoiceRepository.findById(1L))
                .thenReturn(Optional.of(invoice));

        assertThatThrownBy(() -> invoiceService.payInvoice(1L))
                .isInstanceOf(ConflictException.class)
                .hasMessageContaining("cannot be paid");

        verify(invoiceRepository).findById(1L);
    }

    @Test
    void shouldThrowWhenInvoiceFailed() {
        Subscription subscription = new Subscription();
        subscription.setId(1L);

        Invoice invoice = new Invoice();
        invoice.setId(1L);
        invoice.setStatus(Invoice.InvoiceStatus.FAILED);
        invoice.setSubscription(subscription);

        when(invoiceRepository.findById(1L))
                .thenReturn(Optional.of(invoice));

        assertThatThrownBy(() -> invoiceService.payInvoice(1L))
                .isInstanceOf(ConflictException.class)
                .hasMessageContaining("cannot be paid");

        verify(invoiceRepository).findById(1L);
    }
}
