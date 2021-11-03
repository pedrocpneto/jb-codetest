package net.pcpinfo.jbcodetest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class JbCodetestApplication {

	public static void main(String[] args) {
		SpringApplication.run(JbCodetestApplication.class, args);
	}

}
