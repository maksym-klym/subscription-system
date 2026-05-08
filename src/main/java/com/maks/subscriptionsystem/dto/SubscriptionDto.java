package com.maks.subscriptionsystem.dto;

import com.maks.subscriptionsystem.entity.Subscription;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Schema(description = "Subscription response DTO")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class SubscriptionDto {
    @Schema(description = "Subscription ID")
    private Long id;
    @Schema(description = "User ID")
    private Long userId;
    @Schema(description = "Plan ID")
    private Long planId;
    @Schema(description = "Subscription start date")
    private LocalDateTime startDate;
    @Schema(description = "Subscription end date")
    private LocalDateTime endDate;
    @Schema(description = "Subscription status")
    private Subscription.SubscriptionStatus status;
}
