package com.maks.subscriptionsystem.mapper;

import com.maks.subscriptionsystem.dto.SubscriptionDto;
import com.maks.subscriptionsystem.entity.Subscription;

public class SubscriptionMapper {
    public static SubscriptionDto toDto(Subscription subscription){
        SubscriptionDto subscriptionDto = new SubscriptionDto();
        subscriptionDto.setId(subscription.getId());
        subscriptionDto.setStatus(subscription.getStatus());
        subscriptionDto.setUserId(subscription.getUser().getId());
        subscriptionDto.setPlanId(subscription.getPlan().getId());
        subscriptionDto.setStartDate(subscription.getStartDate());
        subscriptionDto.setEndDate(subscription.getEndDate());
        return subscriptionDto;
    }
}
