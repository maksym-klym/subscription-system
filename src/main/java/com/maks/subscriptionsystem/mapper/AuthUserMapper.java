package com.maks.subscriptionsystem.mapper;

import com.maks.subscriptionsystem.dto.AuthUserDto;
import com.maks.subscriptionsystem.entity.User;

public class AuthUserMapper {
    public static AuthUserDto toDto(User user) {
        AuthUserDto authUserDto = new AuthUserDto();
        authUserDto.setId(user.getId());
        authUserDto.setEmail(user.getEmail());
        authUserDto.setFirstName(user.getFirstName());
        authUserDto.setLastName(user.getLastName());
        return authUserDto;
    }
}
