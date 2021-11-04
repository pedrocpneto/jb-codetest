package net.pcpinfo.jbcodetest;

import lombok.extern.slf4j.Slf4j;
import net.pcpinfo.jbcodetest.service.TreasureFinderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
// TODO Web implementation
@Slf4j
public class JbCodetestApplication implements CommandLineRunner {

	@Autowired
	private TreasureFinderService treasureFinderService;

	public static void main(String[] args) {
		SpringApplication.run(JbCodetestApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		log.info("Running");
		treasureFinderService.reset();
		log.info("Done");
	}
}
