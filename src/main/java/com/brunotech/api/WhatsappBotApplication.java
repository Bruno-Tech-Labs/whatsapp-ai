package com.brunotech.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class WhatsappBotApplication {

	public static void main(String[] args) {

		//Carrega as variáveis de ambiente do arquivo .env para testes locais
		Dotenv dotenv = Dotenv.configure()
				.ignoreIfMissing()
				.load();
		
		dotenv.entries().forEach(entry -> {
			System.setProperty(entry.getKey(), entry.getValue());
		});

		SpringApplication.run(WhatsappBotApplication.class, args);
	}

}
