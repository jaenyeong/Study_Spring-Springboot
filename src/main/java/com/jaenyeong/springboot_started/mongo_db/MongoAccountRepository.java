package com.jaenyeong.springboot_started.mongo_db;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface MongoAccountRepository extends MongoRepository<Account, String> {
	Optional<Account> findByEmail(String email);
}
