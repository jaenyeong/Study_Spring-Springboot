package com.jaenyeong.springboot_started.mongo_db;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataMongoTest
public class MongoAccountRepositoryTest {

	@Autowired
	MongoAccountRepository mongoAccountRepository;

	// 테스트시엔 테스트 몽고 DB를 사용하여 운영에 영향을 끼치지 않음
	@Test
	public void findByEmail() throws Exception {
		Account account = new Account();
		account.setUserName("MongoDB Test");
		account.setEmail("MongoDB_Test@gmail.com");

		mongoAccountRepository.save(account);

		Optional<Account> byId = mongoAccountRepository.findById(account.getId());
		assertThat(byId).isNotEmpty();

		Optional<Account> byEmail = mongoAccountRepository.findByEmail(account.getEmail());
		assertThat(byEmail).isNotEmpty();
		assertThat(byEmail.get().getUserName()).isEqualTo("MongoDB Test");
	}
}
