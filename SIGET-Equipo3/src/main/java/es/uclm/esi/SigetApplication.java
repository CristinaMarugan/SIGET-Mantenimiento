package es.uclm.esi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@ComponentScan ({"es.uclm.esi.controller", "es.uclm.esi.model", "es.uclm.esi", "es.uclm.esi.service"})
@EnableMongoRepositories ("es.uclm.esi.repository")
public class SigetApplication {

	public static void main(String[] args) {
		SpringApplication.run(SigetApplication.class, args);
	}

}
