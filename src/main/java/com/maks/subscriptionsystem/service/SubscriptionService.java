package com.maks.subscriptionsystem.service;

import com.maks.subscriptionsystem.dto.filter.SubscriptionFilter;
import com.maks.subscriptionsystem.entity.Plan;
import com.maks.subscriptionsystem.dto.SubscriptionDto;
import com.maks.subscriptionsystem.entity.User;
import com.maks.subscriptionsystem.entity.Subscription;
import com.maks.subscriptionsystem.exception.ItemNotFoundException;
import com.maks.subscriptionsystem.exception.SubscriptionConflictException;
import com.maks.subscriptionsystem.exception.UserConflictException;
import com.maks.subscriptionsystem.mapper.SubscriptionMapper;
import com.maks.subscriptionsystem.repository.PlanRepository;
import com.maks.subscriptionsystem.repository.SubscriptionRepository;
import com.maks.subscriptionsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static com.maks.subscriptionsystem.entity.Subscription.SubscriptionStatus.*;

@RequiredArgsConstructor
@Service
public class SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;
    private final PlanRepository planRepository;
    private final InvoiceService invoiceService;

    public SubscriptionDto get(Long subscriptionId) {
        Subscription subscription = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new ItemNotFoundException("Subscription not found with id: " + subscriptionId));
        return SubscriptionMapper.toDto(subscription);
    }

    public Page<SubscriptionDto> getAll(SubscriptionFilter subscriptionFilter, Pageable pageable) {
        return subscriptionRepository.findAllBy(
                subscriptionFilter.getUserId(),
                subscriptionFilter.getStatus(),
                pageable
        ).map(SubscriptionMapper::toDto);
    }

    @Transactional
    public SubscriptionDto createSubscription(Long userId, Long planId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ItemNotFoundException("User not found with id: " + userId));

        if (hasActiveOrPendingSubscription(user.getId())) {
            throw new UserConflictException("User with ID " + userId + " already has active or pending subscription");
        }

        Plan plan = planRepository.findById(planId)
                .orElseThrow(() -> new ItemNotFoundException("Plan not found with id: " + planId));

        Subscription subscription = new Subscription();
        subscription.setUser(user);
        subscription.setPlan(plan);
        subscription.setStatus(PENDING_PAYMENT);
        subscriptionRepository.save(subscription);
        invoiceService.generateInvoice(subscription);
        return SubscriptionMapper.toDto(subscription);
    }

    public SubscriptionDto cancelSubscription(Long subscriptionId) {
        Subscription subscription = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new ItemNotFoundException("Subscription not found with id: " + subscriptionId));

        if(subscription.getStatus() == CANCELED)
            throw new SubscriptionConflictException("Subscription with ID " + subscriptionId + " is already canceled");

        subscription.setStatus(CANCELED);
        subscriptionRepository.save(subscription);
        return SubscriptionMapper.toDto(subscription);
    }

    public boolean hasActiveOrPendingSubscription(Long userId) {
        return subscriptionRepository.existsByUserIdAndStatusIn(
                userId,
                List.of(ACTIVE, PENDING_PAYMENT)
        );
    }
}
