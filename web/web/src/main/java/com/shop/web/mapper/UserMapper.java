package com.shop.web.mapper;

import com.shop.web.dto.UserDto;
import com.shop.web.models.UserEntity;

public class UserMapper {
    public static UserDto mapToUserDto(UserEntity userEntity) {
        return UserDto.builder()
                .id(userEntity.getId())
                .username(userEntity.getUsername())
                .email(userEntity.getEmail())

                .build();
    }
    public static UserEntity mapToUser(UserDto userDto) {
        return UserEntity.builder()
                .id(userDto.getId())
                .username(userDto.getUsername())
                .email(userDto.getEmail())
                .build();
    }
}
