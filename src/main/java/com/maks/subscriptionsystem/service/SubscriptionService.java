package com.maks.subscriptionsystem.service;

import com.maks.subscriptionsystem.entity.Plan;
import com.maks.subscriptionsystem.dto.SubscriptionDto;
import com.maks.subscriptionsystem.entity.User;
import com.maks.subscriptionsystem.entity.Subscription;
import com.maks.subscriptionsystem.exception.ItemNotFoundException;
import com.maks.subscriptionsystem.mapper.SubscriptionMapper;
import com.maks.subscriptionsystem.repository.PlanRepository;
import com.maks.subscriptionsystem.repository.SubscriptionRepository;
import com.maks.subscriptionsystem.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;
    private final PlanRepository planRepository;
    private final InvoiceService invoiceService;

    public SubscriptionDto get(Long subscriptionId) {
        Subscription subscription = subscriptionRepository.findById(subscriptionId).orElseThrow(() -> new ItemNotFoundException("Subscription not found with id: " + subscriptionId));
        return SubscriptionMapper.toDto(subscription);
    }

    public List<SubscriptionDto> getAll() { return subscriptionRepository.findAll().stream().map(SubscriptionMapper::toDto).toList(); }

    @Transactional
    public SubscriptionDto createSubscription(Long userId, Long planId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ItemNotFoundException("User not found with id: " + userId));
        Plan plan = planRepository.findById(planId).orElseThrow(() -> new ItemNotFoundException("Plan not found with id: " + planId));
        Subscription subscription = new Subscription();
        LocalDateTime creationTime = LocalDateTime.now();
        subscription.setUser(user);
        subscription.setPlan(plan);
        subscription.setStartDate(creationTime);
        subscription.setEndDate(creationTime.plusDays(plan.getDurationDays()));
        subscription.setStatus(Subscription.SubscriptionStatus.ACTIVE);
        subscriptionRepository.save(subscription);
        invoiceService.generateInvoice(subscription);
        return SubscriptionMapper.toDto(subscription);
    }

    public SubscriptionDto cancelSubscription(Long subscriptionId) {
        Subscription subscription = subscriptionRepository.findById(subscriptionId).orElseThrow(() -> new ItemNotFoundException("Subscription not found with id: " + subscriptionId));;
        subscription.setStatus(Subscription.SubscriptionStatus.CANCELED);
        subscriptionRepository.save(subscription);
        return SubscriptionMapper.toDto(subscription);
    }
}
