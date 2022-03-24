package neowise.user.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan
public class NeowiseUserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(NeowiseUserServiceApplication.class, args);
	}

}
