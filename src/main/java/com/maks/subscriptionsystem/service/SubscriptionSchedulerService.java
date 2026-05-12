package com.maks.subscriptionsystem.service;

import com.maks.subscriptionsystem.entity.Subscription;
import com.maks.subscriptionsystem.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static com.maks.subscriptionsystem.entity.Subscription.SubscriptionStatus.ACTIVE;
import static com.maks.subscriptionsystem.entity.Subscription.SubscriptionStatus.EXPIRED;

@Service
@RequiredArgsConstructor
public class SubscriptionSchedulerService {
    private final SubscriptionRepository subscriptionRepository;

    @Scheduled(fixedRate = 60000)
    @Transactional
    public void expireSubscriptions() {
        List<Subscription> expiredSubscriptions = subscriptionRepository.findAllByStatusAndEndDateBefore(
                ACTIVE,
                LocalDateTime.now()
            );

        for(Subscription subscription: expiredSubscriptions)
            subscription.setStatus(EXPIRED);

        subscriptionRepository.saveAll(expiredSubscriptions);
    }
}
