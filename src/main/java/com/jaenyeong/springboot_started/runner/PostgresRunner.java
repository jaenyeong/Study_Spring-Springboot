package com.jaenyeong.springboot_started.runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;

@Component
public class PostgresRunner implements ApplicationRunner {

	@Autowired
	DataSource dataSource;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		try (Connection connection = dataSource.getConnection()) {
			DatabaseMetaData metaData = connection.getMetaData();

			System.out.println("prod db url : " + metaData.getURL());
			System.out.println("prod db driver : " + metaData.getDriverName());
			System.out.println("prod db user name :" + metaData.getUserName());
		}
	}
}
