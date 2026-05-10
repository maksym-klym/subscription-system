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
        WHERE (:name IS NULL OR p.name = :name)
    """)
    Page<Plan> findAllBy(@Param("name") Plan.PlanName name, Pageable pageable);
}
