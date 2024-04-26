package com.shop.web.repository;

import com.shop.web.dto.CartDto;
import com.shop.web.models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
    List<Cart> findByUserId(int userId);
    Cart findByUserUsername(String username);
}
