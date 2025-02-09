package com.warya.base;

import com.warya.base.devonly.DatabaseInitializer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
public class WaryaBaseApplication implements CommandLineRunner {

	private final DatabaseInitializer databaseInitializer;

	public WaryaBaseApplication(DatabaseInitializer databaseInitializer) {
		this.databaseInitializer = databaseInitializer;
	}

	public static void main(String[] args) {
		SpringApplication.run(WaryaBaseApplication.class, args);
	}

	@Override
	@Profile("!prod")
	public void run(String... args) throws Exception {
		databaseInitializer.run(args);
	}
}
