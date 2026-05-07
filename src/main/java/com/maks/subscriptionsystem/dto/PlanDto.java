package com.maks.subscriptionsystem.dto;

import com.maks.subscriptionsystem.entity.Plan;
import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PlanDto {
    private Long id;
    private BigDecimal price;
    private Integer durationDays;
    private Plan.PlanName name;
}
