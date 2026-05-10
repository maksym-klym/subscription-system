package com.maks.subscriptionsystem.mapper;

import com.maks.subscriptionsystem.dto.UserDto;
import com.maks.subscriptionsystem.entity.User;

public class UserMapper {
    public static UserDto toDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        return userDto;
    }
}
