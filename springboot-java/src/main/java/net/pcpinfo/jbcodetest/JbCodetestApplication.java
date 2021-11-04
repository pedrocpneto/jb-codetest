package net.pcpinfo.jbcodetest;

import lombok.extern.slf4j.Slf4j;
import net.pcpinfo.jbcodetest.service.TreasureFinderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.retry.annotation.EnableRetry;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@SpringBootApplication
@Slf4j
@EnableRetry
public class JbCodetestApplication {

	@Autowired
	private TreasureFinderService treasureFinderService;



	public static void main(String[] args) {
		SpringApplication.run(JbCodetestApplication.class, args);
	}
}
