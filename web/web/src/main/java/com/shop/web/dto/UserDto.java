package com.shop.web.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
    private int id;
    private String username;
    private String email;
    private CartDto cartDto;
}
