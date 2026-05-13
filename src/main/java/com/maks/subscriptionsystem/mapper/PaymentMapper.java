package com.maks.subscriptionsystem.mapper;

import com.maks.subscriptionsystem.dto.PaymentDto;
import com.maks.subscriptionsystem.entity.Payment;

public class PaymentMapper {
    public static PaymentDto toDto(Payment payment) {
        PaymentDto paymentDto = new PaymentDto();
        paymentDto.setId(payment.getId());
        paymentDto.setInvoiceId(payment.getInvoice().getId());
        paymentDto.setAmount(payment.getAmount());
        paymentDto.setPaidAt(payment.getPaidAt());
        return paymentDto;
    }
}
