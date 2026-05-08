package com.maks.subscriptionsystem.repository;

import com.maks.subscriptionsystem.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    List<Invoice> findAllBySubscriptionId(Long subscriptionId);
}
