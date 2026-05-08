package com.maks.subscriptionsystem.dto;

import com.maks.subscriptionsystem.entity.Invoice;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class InvoiceDto {
    private Long id;
    private Long subscriptionId;
    private BigDecimal amount;
    private LocalDateTime dueDate;
    private Invoice.InvoiceStatus status;
}
