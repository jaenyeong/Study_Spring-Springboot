package com.jaenyeong.springboot_started.springboot_jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
//import org.springframework.data.jpa.repository.Query;

public interface AccountRepository extends JpaRepository<Account, Long> {
	// Native query 작성시
//	@Query(nativeQuery = true, value = "SELECT * FROM account WHERE user_name = ?1")
	Optional<Account> findByUserName(String userName);
}
