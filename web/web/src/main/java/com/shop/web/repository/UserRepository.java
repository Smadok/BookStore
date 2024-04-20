package com.shop.web.repository;

import com.shop.web.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Integer> {
    UserEntity findByEmail(String email);
    UserEntity findByUsername(String userName);
    UserEntity findFirstByUsername(String username);
}
