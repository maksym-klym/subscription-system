package com.maks.subscriptionsystem.service;

import com.maks.subscriptionsystem.dto.AuthUserDto;
import com.maks.subscriptionsystem.dto.CreateUserDto;
import com.maks.subscriptionsystem.dto.UserDto;
import com.maks.subscriptionsystem.entity.User;
import com.maks.subscriptionsystem.exception.ConflictException;
import com.maks.subscriptionsystem.exception.ItemNotFoundException;
import com.maks.subscriptionsystem.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private UserService userService;

    @Test
    void shouldCreateUserSuccessfully() {
        CreateUserDto dto = new CreateUserDto();
        dto.setEmail("test@mail.com");
        dto.setPassword("rawPass");

        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setEmail(dto.getEmail());
        savedUser.setPassword("encodedPass");

        when(userRepository.existsByEmail(dto.getEmail()))
                .thenReturn(false);

        when(passwordEncoder.encode(dto.getPassword()))
                .thenReturn("encodedPass");

        when(userRepository.save(any(User.class)))
                .thenReturn(savedUser);

        UserDto result = userService.createUser(dto);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getEmail()).isEqualTo(dto.getEmail());

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);

        verify(userRepository).existsByEmail(dto.getEmail());
        verify(passwordEncoder).encode(dto.getPassword());
        verify(userRepository).save(userCaptor.capture());

        User saved = userCaptor.getValue();
        assertThat(saved.getEmail()).isEqualTo(dto.getEmail());
        assertThat(saved.getPassword()).isEqualTo("encodedPass");
    }

    @Test
    void shouldThrowWhenEmailAlreadyExists() {
        CreateUserDto dto = new CreateUserDto();
        dto.setEmail("test@mail.com");
        dto.setPassword("pass");

        when(userRepository.existsByEmail(dto.getEmail()))
                .thenReturn(true);

        assertThatThrownBy(() -> userService.createUser(dto))
                .isInstanceOf(ConflictException.class);

        verify(userRepository).existsByEmail(dto.getEmail());
        verify(userRepository, never()).save(any());
        verify(passwordEncoder, never()).encode(any());
    }

    @Test
    void shouldReturnUserById() {
        Long userId = 1L;

        User user = new User();
        user.setId(userId);
        user.setEmail("test@mail.com");

        when(userRepository.findById(userId))
                .thenReturn(Optional.of(user));

        UserDto result = userService.get(userId);

        assertThat(result.getId()).isEqualTo(userId);
        assertThat(result.getEmail()).isEqualTo("test@mail.com");

        verify(userRepository).findById(userId);
    }

    @Test
    void shouldThrowWhenUserNotFoundById() {
        Long userId = 99L;

        when(userRepository.findById(userId))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.get(userId))
                .isInstanceOf(ItemNotFoundException.class);

        verify(userRepository).findById(userId);
    }

    @Test
    void shouldReturnAuthUserDto() {
        String email = "test@mail.com";

        User user = new User();
        user.setId(1L);
        user.setEmail(email);

        when(userRepository.findByEmail(email))
                .thenReturn(Optional.of(user));

        AuthUserDto result = userService.getAuthUser(email);

        assertThat(result).isNotNull();
        assertThat(result.getEmail()).isEqualTo(email);

        verify(userRepository).findByEmail(email);
    }

    @Test
    void shouldThrowWhenAuthUserNotFound() {
        String email = "missing@mail.com";

        when(userRepository.findByEmail(email))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.getAuthUser(email))
                .isInstanceOf(ItemNotFoundException.class);

        verify(userRepository).findByEmail(email);
    }
}