package com.maks.subscriptionsystem.service;

import com.maks.subscriptionsystem.dto.AuthUserDto;
import com.maks.subscriptionsystem.dto.CreateUserDto;
import com.maks.subscriptionsystem.dto.UserDto;
import com.maks.subscriptionsystem.dto.filter.UserFilter;
import com.maks.subscriptionsystem.entity.User;
import com.maks.subscriptionsystem.exception.ConflictException;
import com.maks.subscriptionsystem.exception.ItemNotFoundException;
import com.maks.subscriptionsystem.mapper.AuthUserMapper;
import com.maks.subscriptionsystem.mapper.UserMapper;
import com.maks.subscriptionsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    public UserDto createUser(CreateUserDto userDto) {
        User user = new User();
        if(userRepository.existsByEmail(userDto.getEmail()))
            throw new ConflictException("Email already exists");
        user.setEmail(userDto.getEmail());
        user.setPassword(encoder.encode(userDto.getPassword()));
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setCreatedAt(LocalDateTime.now());
        User savedUser = userRepository.save(user);
        return UserMapper.toDto(savedUser);
    }

    public UserDto get(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("User not found with id: " + id));
        return UserMapper.toDto(user);
    }

    public Page<UserDto> getAll(UserFilter userFilter, Pageable pageable) {
        return userRepository.findAllBy(
                userFilter.getEmail(),
                userFilter.getFirstName(),
                userFilter.getLastName(),
                pageable
        ).map(UserMapper::toDto);
    }

    public AuthUserDto getAuthUser(String email) {
        return userRepository.findByEmail(email).map(AuthUserMapper::toDto)
                .orElseThrow(() -> new ItemNotFoundException("User not found with email: " + email));
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
