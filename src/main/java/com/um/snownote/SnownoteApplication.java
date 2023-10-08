package com.um.snownote;

import com.um.snownote.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SnownoteApplication {

	public static void main(String[] args) {
		SpringApplication.run(SnownoteApplication.class, args);
	}

}
