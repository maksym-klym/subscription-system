package com.maks.subscriptionsystem.service;

import com.maks.subscriptionsystem.dto.PaymentDto;
import com.maks.subscriptionsystem.entity.Payment;
import com.maks.subscriptionsystem.exception.ItemNotFoundException;
import com.maks.subscriptionsystem.mapper.PaymentMapper;
import com.maks.subscriptionsystem.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;

    public PaymentDto get(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Payment not found with id: " + id));
        return PaymentMapper.toDto(payment);
    }

    public List<PaymentDto> getAll() {
        return paymentRepository.findAll().stream().map(PaymentMapper::toDto).toList();
    }
}
