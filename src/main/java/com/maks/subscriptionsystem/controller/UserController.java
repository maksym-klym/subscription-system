package com.maks.subscriptionsystem.controller;

import com.maks.subscriptionsystem.dto.CreateUserDto;
import com.maks.subscriptionsystem.dto.UserDto;
import com.maks.subscriptionsystem.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Users", description = "User management APIs")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @Operation(summary = "Get user by ID")
    @GetMapping("/{id}")
    public UserDto get(@Parameter(description = "User ID") @PathVariable Long id) { return userService.getUserById(id); }

    @Operation(summary = "Get all users")
    @GetMapping
    public Page<UserDto> getAll(@ParameterObject @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) { return userService.getAll(pageable); }

    @Operation(summary = "Create user")
    @PostMapping
    public UserDto create(@RequestBody @Valid CreateUserDto userDto) { return userService.createUser(userDto); }
}
