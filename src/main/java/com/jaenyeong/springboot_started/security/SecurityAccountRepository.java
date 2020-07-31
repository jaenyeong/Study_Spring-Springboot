package com.jaenyeong.springboot_started.security;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SecurityAccountRepository extends JpaRepository<SecurityAccount, Long> {
	Optional<SecurityAccount> findByUserName(String username);
}
