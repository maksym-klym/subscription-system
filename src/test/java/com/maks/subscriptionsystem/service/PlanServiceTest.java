package com.maks.subscriptionsystem.service;

import com.maks.subscriptionsystem.dto.PlanDto;
import com.maks.subscriptionsystem.entity.Plan;
import com.maks.subscriptionsystem.exception.ItemNotFoundException;
import com.maks.subscriptionsystem.repository.PlanRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PlanServiceTest {
    @Mock
    private PlanRepository planRepository;
    @InjectMocks
    private PlanService planService;

    @Test
    void shouldReturnPlanById() {
        Plan plan = new Plan();
        plan.setId(1L);

        when(planRepository.findById(1L)).thenReturn(Optional.of(plan));

        PlanDto result = planService.get(1L);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);

        verify(planRepository).findById(1L);
    }

    @Test
    void shouldThrowWhenPlanNotFound() {
        when(planRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> planService.get(1L))
                .isInstanceOf(ItemNotFoundException.class)
                .hasMessage("Plan not found with id: 1");

        verify(planRepository).findById(1L);
    }

    @Test
    void shouldReturnAllPlans() {
        Plan plan1 = new Plan();
        plan1.setId(1L);

        Plan plan2 = new Plan();
        plan2.setId(2L);

        Pageable pageable = PageRequest.of(0, 10);
        Page<Plan> page = new PageImpl<>(List.of(plan1, plan2));

        when(planRepository.findAllBy(Plan.PlanName.BASIC, pageable)).thenReturn(page);

        Page<PlanDto> result = planService.getAll(Plan.PlanName.BASIC, pageable);

        assertThat(result.getContent()).hasSize(2);
        assertThat(result.getContent())
                .extracting(PlanDto::getId)
                .containsExactlyInAnyOrder(1L, 2L);

        verify(planRepository).findAllBy(Plan.PlanName.BASIC, pageable);
    }
}
