package com.maks.subscriptionsystem.service;

import com.maks.subscriptionsystem.dto.SubscriptionDto;
import com.maks.subscriptionsystem.dto.filter.SubscriptionFilter;
import com.maks.subscriptionsystem.entity.Plan;
import com.maks.subscriptionsystem.entity.Subscription;
import com.maks.subscriptionsystem.entity.User;
import com.maks.subscriptionsystem.exception.ConflictException;
import com.maks.subscriptionsystem.exception.ItemNotFoundException;
import com.maks.subscriptionsystem.repository.PlanRepository;
import com.maks.subscriptionsystem.repository.SubscriptionRepository;
import com.maks.subscriptionsystem.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SubscriptionServiceTest {
    @Mock
    private SubscriptionRepository subscriptionRepository;
    @Mock
    private PlanRepository planRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private InvoiceService invoiceService;
    @InjectMocks
    private SubscriptionService subscriptionService;

    @Test
    void shouldCreateSubscriptionSuccessfully() {
        Long userId = 1L;
        Long planId = 2L;

        User user = new User();
        user.setId(userId);
        user.setEmail("test@mail.com");

        Plan plan = new Plan();
        plan.setId(planId);
        plan.setName(Plan.PlanName.BASIC);

        when(userRepository.findById(userId))
                .thenReturn(Optional.of(user));

        when(subscriptionRepository.existsByUserIdAndStatusIn(
                eq(userId),
                anyList()
        )).thenReturn(false);

        when(planRepository.findById(planId))
                .thenReturn(Optional.of(plan));

        when(subscriptionRepository.save(any(Subscription.class)))
                .thenAnswer(invocation -> {
                    Subscription s = invocation.getArgument(0);
                    s.setId(10L);
                    return s;
                });

        SubscriptionDto result =
                subscriptionService.createSubscription(userId, planId);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(10L);
        assertThat(result.getStatus())
                .isEqualTo(Subscription.SubscriptionStatus.PENDING_PAYMENT);

        ArgumentCaptor<Subscription> captor =
                ArgumentCaptor.forClass(Subscription.class);

        verify(userRepository).findById(userId);

        verify(subscriptionRepository)
                .existsByUserIdAndStatusIn(eq(userId), anyList());

        verify(planRepository).findById(planId);

        verify(subscriptionRepository).save(captor.capture());

        verify(invoiceService).generateInvoice(any(Subscription.class));

        Subscription saved = captor.getValue();

        assertThat(saved.getUser()).isEqualTo(user);
        assertThat(saved.getPlan()).isEqualTo(plan);
        assertThat(saved.getStatus())
                .isEqualTo(Subscription.SubscriptionStatus.PENDING_PAYMENT);
    }

    @Test
    void shouldThrowWhenUserNotFound() {
        Long userId = 1L;
        Long planId = 2L;

        when(userRepository.findById(userId))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() ->
                subscriptionService.createSubscription(userId, planId)
        ).isInstanceOf(ItemNotFoundException.class);

        verify(userRepository).findById(userId);

        verify(planRepository, never()).findById(anyLong());
        verify(subscriptionRepository, never()).save(any());
        verify(invoiceService, never()).generateInvoice(any());
    }

    @Test
    void shouldThrowWhenPlanNotFound() {
        Long userId = 1L;
        Long planId = 2L;

        User user = new User();
        user.setId(userId);
        user.setEmail("test@mail.com");

        when(userRepository.findById(userId))
                .thenReturn(Optional.of(user));

        when(subscriptionRepository.existsByUserIdAndStatusIn(
                eq(userId),
                anyList()
        )).thenReturn(false);

        when(planRepository.findById(planId))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() ->
                subscriptionService.createSubscription(userId, planId)
        ).isInstanceOf(ItemNotFoundException.class);

        verify(userRepository).findById(userId);
        verify(subscriptionRepository).existsByUserIdAndStatusIn(eq(userId), anyList());
        verify(planRepository).findById(planId);

        verify(subscriptionRepository, never()).save(any());
        verify(invoiceService, never()).generateInvoice(any());
    }

    @Test
    void shouldThrowWhenUserAlreadyHasActiveOrPendingSubscription() {
        Long userId = 1L;
        Long planId = 2L;

        User user = new User();
        user.setId(userId);

        when(userRepository.findById(userId))
                .thenReturn(Optional.of(user));

        when(subscriptionRepository.existsByUserIdAndStatusIn(
                anyLong(),
                anyList()
        )).thenReturn(true);

        assertThatThrownBy(() ->
                subscriptionService.createSubscription(userId, planId)
        ).isInstanceOf(ConflictException.class);

        verify(userRepository).findById(userId);

        verify(subscriptionRepository)
                .existsByUserIdAndStatusIn(anyLong(), anyList());

        verify(planRepository, never()).findById(anyLong());
        verify(subscriptionRepository, never()).save(any());
        verify(invoiceService, never()).generateInvoice(any());
    }

    @Test
    void shouldThrowWhenUserAlreadyHasPendingSubscription() {
        Long userId = 1L;
        Long planId = 2L;

        User user = new User();
        user.setId(userId);

        when(userRepository.findById(userId))
                .thenReturn(Optional.of(user));

        when(subscriptionRepository.existsByUserIdAndStatusIn(
                anyLong(),
                anyList()
        )).thenReturn(true);

        assertThatThrownBy(() ->
                subscriptionService.createSubscription(userId, planId)
        ).isInstanceOf(ConflictException.class);

        verify(userRepository).findById(userId);

        verify(subscriptionRepository)
                .existsByUserIdAndStatusIn(anyLong(), anyList());

        verify(planRepository, never()).findById(anyLong());
        verify(subscriptionRepository, never()).save(any());
        verify(invoiceService, never()).generateInvoice(any());
    }

    @Test
    void shouldCancelSubscriptionSuccessfully() {
        Long subscriptionId = 1L;

        User user = new User();
        user.setId(1L);

        Plan plan = new Plan();
        plan.setId(2L);

        Subscription subscription = new Subscription();
        subscription.setId(subscriptionId);
        subscription.setUser(user);
        subscription.setPlan(plan);
        subscription.setStatus(Subscription.SubscriptionStatus.ACTIVE);

        when(subscriptionRepository.findById(subscriptionId))
                .thenReturn(Optional.of(subscription));

        when(subscriptionRepository.save(any(Subscription.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        SubscriptionDto result =
                subscriptionService.cancelSubscription(subscriptionId);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(subscriptionId);
        assertThat(result.getStatus())
                .isEqualTo(Subscription.SubscriptionStatus.CANCELED);

        ArgumentCaptor<Subscription> captor =
                ArgumentCaptor.forClass(Subscription.class);

        verify(subscriptionRepository).findById(subscriptionId);
        verify(subscriptionRepository).save(captor.capture());

        Subscription saved = captor.getValue();

        assertThat(saved.getStatus())
                .isEqualTo(Subscription.SubscriptionStatus.CANCELED);
    }

    @Test
    void shouldThrowWhenSubscriptionAlreadyCanceled() {
        Long subscriptionId = 1L;

        Subscription subscription = new Subscription();
        subscription.setId(subscriptionId);
        subscription.setStatus(Subscription.SubscriptionStatus.CANCELED);

        when(subscriptionRepository.findById(subscriptionId))
                .thenReturn(Optional.of(subscription));

        assertThatThrownBy(() ->
                subscriptionService.cancelSubscription(subscriptionId)
        ).isInstanceOf(ConflictException.class);

        verify(subscriptionRepository).findById(subscriptionId);
        verify(subscriptionRepository, never()).save(any());
    }

    @Test
    void shouldThrowWhenCancelingMissingSubscription() {
        Long subscriptionId = 1L;

        when(subscriptionRepository.findById(subscriptionId))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() ->
                subscriptionService.cancelSubscription(subscriptionId)
        ).isInstanceOf(ItemNotFoundException.class);

        verify(subscriptionRepository).findById(subscriptionId);
        verify(subscriptionRepository, never()).save(any());
    }

    @Test
    void shouldReturnSubscriptionById() {
        Long subscriptionId = 1L;

        User user = new User();
        user.setId(1L);

        Plan plan = new Plan();
        plan.setId(2L);

        Subscription subscription = new Subscription();
        subscription.setId(subscriptionId);
        subscription.setUser(user);
        subscription.setPlan(plan);
        subscription.setStatus(Subscription.SubscriptionStatus.ACTIVE);

        when(subscriptionRepository.findById(subscriptionId))
                .thenReturn(Optional.of(subscription));

        SubscriptionDto result = subscriptionService.get(subscriptionId);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(subscriptionId);
        assertThat(result.getStatus())
                .isEqualTo(Subscription.SubscriptionStatus.ACTIVE);

        verify(subscriptionRepository).findById(subscriptionId);
    }

    @Test
    void shouldThrowWhenSubscriptionNotFound() {
        Long subscriptionId = 1L;

        when(subscriptionRepository.findById(subscriptionId))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() ->
                subscriptionService.get(subscriptionId)
        ).isInstanceOf(ItemNotFoundException.class);

        verify(subscriptionRepository).findById(subscriptionId);
    }

    @Test
    void shouldReturnFilteredSubscriptions() {
        SubscriptionFilter filter = new SubscriptionFilter();
        filter.setUserId(1L);
        filter.setStatus(Subscription.SubscriptionStatus.ACTIVE);

        Pageable pageable = PageRequest.of(0, 10);

        User user = new User();
        user.setId(1L);

        Plan plan = new Plan();
        plan.setId(2L);

        Subscription subscription = new Subscription();
        subscription.setId(1L);
        subscription.setUser(user);
        subscription.setPlan(plan);
        subscription.setStatus(Subscription.SubscriptionStatus.ACTIVE);

        Page<Subscription> page =
                new PageImpl<>(List.of(subscription));

        when(subscriptionRepository.findAllBy(
                filter.getUserId(),
                filter.getStatus(),
                pageable
        )).thenReturn(page);

        Page<SubscriptionDto> result =
                subscriptionService.getAll(filter, pageable);

        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getId()).isEqualTo(1L);
        assertThat(result.getContent().get(0).getStatus())
                .isEqualTo(Subscription.SubscriptionStatus.ACTIVE);

        verify(subscriptionRepository)
                .findAllBy(filter.getUserId(), filter.getStatus(), pageable);
    }

    @Test
    void shouldReturnTrueWhenUserHasActiveOrPendingSubscription() {
        Long userId = 1L;

        when(subscriptionRepository.existsByUserIdAndStatusIn(
                eq(userId),
                anyList()
        )).thenReturn(true);

        boolean result = subscriptionService.hasActiveOrPendingSubscription(userId);

        assertThat(result).isTrue();

        verify(subscriptionRepository)
                .existsByUserIdAndStatusIn(eq(userId), anyList());
    }

    @Test
    void shouldReturnFalseWhenUserHasNoActiveOrPendingSubscription() {
        Long userId = 1L;

        when(subscriptionRepository.existsByUserIdAndStatusIn(
                eq(userId),
                anyList()
        )).thenReturn(false);

        boolean result = subscriptionService.hasActiveOrPendingSubscription(userId);

        assertThat(result).isFalse();

        verify(subscriptionRepository)
                .existsByUserIdAndStatusIn(eq(userId), anyList());
    }
}
