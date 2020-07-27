package com.jaenyeong.springboot_started.in_memory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
//import java.sql.Statement;

@Component
public class H2Runner implements ApplicationRunner {
//public class H2Runner {

	@Autowired
	DataSource dataSource;

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		try (Connection connection = dataSource.getConnection()) {

			String url = connection.getMetaData().getURL();
			String userName = connection.getMetaData().getUserName();

			System.out.println("DataSource Connection url : " + url);
			System.out.println("DataSource Connection userName : " + userName);
			System.out.println("DataSource Connection type : " + dataSource.getClass());

//			Statement statement = connection.createStatement();
//			String createSql = "CREATE TABLE USER (ID INTEGER NOT NULL, name VARCHAR(255), PRIMARY KEY (id))";
//			statement.executeUpdate(createSql);
//
//			String insertSql = "INSERT INTO USER VALUES (1, 'jaenyeong')";
//			jdbcTemplate.execute(insertSql);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
