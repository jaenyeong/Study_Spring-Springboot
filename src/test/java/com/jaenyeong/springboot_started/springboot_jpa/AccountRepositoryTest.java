package com.jaenyeong.springboot_started.springboot_jpa;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
// 슬라이싱 테스트
@DataJpaTest
// 통합 테스트, 모든 빈이 등록되기 때문에 postgres를 사용하게 됨
//@SpringBootTest
public class AccountRepositoryTest {

	@Autowired
	DataSource dataSource;

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	AccountRepository accountRepository;

	@Test
	public void di() throws SQLException {
		try (Connection connection = dataSource.getConnection()) {
			DatabaseMetaData metaData = connection.getMetaData();

			System.out.println("test db url : " + metaData.getURL());
			System.out.println("test db url : " + metaData.getDriverName());
			System.out.println("test db url : " + metaData.getUserName());
		}

		Account account = new Account();
		account.setUserName("jaenyeong");
		account.setPassword("pass");

		Account newAccount = accountRepository.save(account);

		assertThat(newAccount).isNotNull();

		Optional<Account> existingAccount = accountRepository.findByUserName(newAccount.getUserName());
		assertThat(existingAccount).isNotEmpty();

		Optional<Account> nonExistingAccount = accountRepository.findByUserName("noah");
		assertThat(nonExistingAccount).isEmpty();
	}
}
