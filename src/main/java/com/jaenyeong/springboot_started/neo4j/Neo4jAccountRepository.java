package com.jaenyeong.springboot_started.neo4j;

import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface Neo4jAccountRepository extends Neo4jRepository<Account, Long> {
}
