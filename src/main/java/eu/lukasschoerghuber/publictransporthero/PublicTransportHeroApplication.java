package eu.lukasschoerghuber.publictransporthero;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.ApiContextInitializer;

@SpringBootApplication
public class PublicTransportHeroApplication {

	public static void main(String[] args) {
		ApiContextInitializer.init();

		SpringApplication.run(PublicTransportHeroApplication.class, args);
	}

}
