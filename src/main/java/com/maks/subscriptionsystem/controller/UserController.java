package com.maks.subscriptionsystem.controller;

import com.maks.subscriptionsystem.dto.CreateUserDto;
import com.maks.subscriptionsystem.dto.UserDto;
import com.maks.subscriptionsystem.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    public UserDto get(@PathVariable Long id) { return userService.getUserById(id); }

    @PostMapping
    public UserDto create(@RequestBody @Valid CreateUserDto userDto) { return userService.createUser(userDto); }
}
