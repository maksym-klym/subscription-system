package com.maks.subscriptionsystem.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "subscription_plans")
@Entity
public class Plan {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    @Column(nullable = false)
    private BigDecimal price;
    @Column(nullable = false)
    private Integer durationDays;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PlanName name = PlanName.BASIC;

    public enum PlanName{
        BASIC,
        PRO,
        ENTERPRISE
    }
}
