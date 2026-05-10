package com.maks.subscriptionsystem.repository;

import com.maks.subscriptionsystem.entity.Plan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanRepository extends JpaRepository<Plan, Long> {
    @Query("""
        SELECT p FROM Plan p
        WHERE (:planName IS NULL OR p.name = :planName)
    """)
    Page<Plan> findAllBy(@Param("planName") Plan.PlanName planName, Pageable pageable);
}
