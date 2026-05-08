package com.maks.subscriptionsystem.dto;

import com.maks.subscriptionsystem.entity.Plan;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;

@Schema(description = "Plan response DTO")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PlanDto {
    @Schema(description = "Plan ID")
    private Long id;
    @Schema(description = "Plan price")
    private BigDecimal price;
    @Schema(description = "Plan duration days")
    private Integer durationDays;
    @Schema(description = "Plan name")
    private Plan.PlanName name;
}
