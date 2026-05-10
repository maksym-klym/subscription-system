package com.maks.subscriptionsystem.service;

import com.maks.subscriptionsystem.dto.CreateUserDto;
import com.maks.subscriptionsystem.dto.UserDto;
import com.maks.subscriptionsystem.dto.filter.UserFilter;
import com.maks.subscriptionsystem.entity.User;
import com.maks.subscriptionsystem.exception.ItemNotFoundException;
import com.maks.subscriptionsystem.mapper.UserMapper;
import com.maks.subscriptionsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    public UserDto createUser(CreateUserDto userDto) {
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setCreatedAt(LocalDateTime.now());
        User savedUser = userRepository.save(user);
        return UserMapper.toDto(savedUser);
    }

    public Page<UserDto> getAll(UserFilter userFilter, Pageable pageable) {
        return userRepository.findAllBy(
                userFilter.getEmail(),
                userFilter.getFirstName(),
                userFilter.getLastName(),
                pageable
        ).map(UserMapper::toDto);
    }

    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("User not found with id: " + id));
        return UserMapper.toDto(user);
    }

    public UserDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ItemNotFoundException("User not found with email: " + email));
        return UserMapper.toDto(user);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
