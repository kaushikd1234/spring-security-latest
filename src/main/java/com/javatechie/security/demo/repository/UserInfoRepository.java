package com.javatechie.security.demo.repository;

import com.javatechie.security.demo.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {

    Optional<UserInfo> findByName(String username);
}
