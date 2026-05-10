package com.maks.subscriptionsystem.repository;

import com.maks.subscriptionsystem.entity.Invoice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    @Query("""
        SELECT i FROM Invoice i
        WHERE (:status IS NULL OR i.status = :status)
    """)
    Page<Invoice> findAllBy(@Param("status") Invoice.InvoiceStatus status, Pageable pageable);
    Page<Invoice> findAllBySubscriptionId(Long subscriptionId, Pageable pageable);
}
