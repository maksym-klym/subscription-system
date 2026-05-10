package com.maks.subscriptionsystem.dto.filter;

import com.maks.subscriptionsystem.entity.Subscription;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Schema(description = "Subscription filter DTO")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class SubscriptionFilter {
    @Schema(description = "User ID")
    private Long userId;
    @Schema(description = "Subscription status", implementation = Subscription.SubscriptionStatus.class)
    private Subscription.SubscriptionStatus status;
}
