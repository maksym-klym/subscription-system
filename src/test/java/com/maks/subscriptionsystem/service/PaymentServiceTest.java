package com.maks.subscriptionsystem.service;

import com.maks.subscriptionsystem.dto.PaymentDto;
import com.maks.subscriptionsystem.entity.Invoice;
import com.maks.subscriptionsystem.entity.Payment;
import com.maks.subscriptionsystem.exception.ItemNotFoundException;
import com.maks.subscriptionsystem.repository.PaymentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {
    @Mock
    private PaymentRepository paymentRepository;
    @InjectMocks
    private PaymentService paymentService;

    @Test
    void shouldReturnPaymentById() {
        Invoice invoice = new Invoice();
        invoice.setId(10L);

        Payment payment = new Payment();
        payment.setId(1L);
        payment.setInvoice(invoice);

        when(paymentRepository.findById(1L)).thenReturn(Optional.of(payment));

        PaymentDto result = paymentService.get(1L);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);

        verify(paymentRepository).findById(1L);
    }

    @Test
    void shouldThrowWhenPaymentNotFound() {
        when(paymentRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> paymentService.get(1L))
                .isInstanceOf(ItemNotFoundException.class)
                .hasMessage("Payment not found with id: 1");

        verify(paymentRepository).findById(1L);
    }

    @Test
    void shouldReturnAllPayments() {
        Invoice invoice1 = new Invoice();
        invoice1.setId(1L);
        Invoice invoice2 = new Invoice();
        invoice2.setId(2L);

        Payment payment1 = new Payment();
        payment1.setId(1L);
        payment1.setInvoice(invoice1);
        Payment payment2 = new Payment();
        payment2.setId(2L);
        payment2.setInvoice(invoice2);

        when(paymentRepository.findAll()).thenReturn(List.of(payment1, payment2));

        List<PaymentDto> result = paymentService.getAll();

        assertThat(result).hasSize(2);
        assertThat(result)
                .extracting(PaymentDto::getId)
                .containsExactlyInAnyOrder(1L, 2L);

        verify(paymentRepository).findAll();
    }
}