package com.maks.subscriptionsystem.dto;

import com.maks.subscriptionsystem.entity.Subscription;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class SubscriptionDto {
    private Long id;
    private Long userId;
    private Long planId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Subscription.SubscriptionStatus status;
}
