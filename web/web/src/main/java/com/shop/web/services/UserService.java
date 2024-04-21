package com.shop.web.services;

import com.shop.web.dto.RegistrationDto;
import com.shop.web.models.Cart;
import com.shop.web.models.UserEntity;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    void saveUser(RegistrationDto registrationDto);

    UserEntity findByEmail(String email);

    UserEntity findByUsername(String username);
}
