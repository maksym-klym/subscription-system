package com.maks.subscriptionsystem.dto;

import com.maks.subscriptionsystem.entity.Invoice;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "Invoice response DTO")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class InvoiceDto {
    @Schema(description = "Invoice ID")
    private Long id;
    @Schema(description = "Subscription ID")
    private Long subscriptionId;
    @Schema(description = "Invoice amount")
    private BigDecimal amount;
    @Schema(description = "Invoice due date")
    private LocalDateTime dueDate;
    @Schema(description = "Invoice status")
    private Invoice.InvoiceStatus status;
}
