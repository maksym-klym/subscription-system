package com.maks.subscriptionsystem.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "Payment response DTO")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PaymentDto {
    @Schema(description = "Payment ID")
    private Long id;
    @Schema(description = "Invoice ID")
    private Long invoiceId;
    @Schema(description = "Payment amount")
    private BigDecimal amount;
    @Schema(description = "Payment paid date")
    private LocalDateTime paidAt;
}
