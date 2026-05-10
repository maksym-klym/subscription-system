package com.maks.subscriptionsystem.repository;

import com.maks.subscriptionsystem.entity.Subscription;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    @Query("""
        SELECT s FROM Subscription s
        WHERE (:status IS NULL OR s.status = :status)
        AND (:userId IS NULL OR s.user.id = :userId)   
    """)
    Page<Subscription> findAllBy(
            @Param("userId") Long userId,
            @Param("status")Subscription.SubscriptionStatus status,
            Pageable pageable
    );
}
