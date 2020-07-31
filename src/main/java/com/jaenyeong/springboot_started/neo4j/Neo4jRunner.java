package com.jaenyeong.springboot_started.neo4j;

import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class Neo4jRunner implements ApplicationRunner {

	@Autowired
	SessionFactory sessionFactory;

	@Autowired
	Neo4jAccountRepository neo4jAccountRepository;

	@Override
	public void run(ApplicationArguments args) throws Exception {
//		saveBySession();

		saveByRepository();

		System.out.println("Neo4j finished");
	}

	private void saveByRepository() {
		Account account = new Account();
		account.setUserName("Repo");
		account.setEmail("Repo1@gmail.com");

		Role role = new Role();
		role.setName("test");

		account.getRoles().add(role);

		neo4jAccountRepository.save(account);
	}

	private void saveBySession() {
		Account account = new Account();
//		account.setUserName("jaenyeong");
//		account.setEmail("jaenyeong.dev@gmail.com");

		// Relationship
		account.setUserName("Master");
		account.setEmail("master@gmail.com");

		Role role = new Role();
		role.setName("admin");

		account.getRoles().add(role);

		Session session = sessionFactory.openSession();
		session.save(account);

		// 세션안에 캐싱된 객체를 클리어
//		session.clear();

		// 닫지 않으면 앱이 종료되지 않음
		sessionFactory.close();
	}
}
