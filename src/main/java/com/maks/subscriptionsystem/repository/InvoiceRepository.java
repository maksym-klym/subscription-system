package com.maks.subscriptionsystem.repository;

import com.maks.subscriptionsystem.entity.Invoice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    Page<Invoice> findAllBySubscriptionId(Long subscriptionId, Pageable pageable);
}
