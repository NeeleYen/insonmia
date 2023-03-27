package com.test.Insomnia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

//@EnableGlobalMethodSecurity(securedEnabled=true) // 開啟註解才可使用
@SpringBootApplication
@EnableJpaAuditing
public class InsomniaApplication {

	public static void main(String[] args) {
		SpringApplication.run(InsomniaApplication.class, args);
	}

}
