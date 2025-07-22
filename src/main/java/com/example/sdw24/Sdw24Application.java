package com.example.sdw24;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import com.example.sdw24.application.AskChampionsUseCase;
import com.example.sdw24.application.ListChampionsUseCase;
import com.example.sdw24.domain.ports.ChampionsRepository;
import com.example.sdw24.domain.ports.GenerativeAiService;

@EnableFeignClients
@SpringBootApplication
public class Sdw24Application {

	public static void main(String[] args) {
		SpringApplication.run(Sdw24Application.class, args);
	}
	
	@Bean
	public ListChampionsUseCase provideListChampionsUseCase(ChampionsRepository repository) {
		return new ListChampionsUseCase(repository);
	}
	
	@Bean
	public AskChampionsUseCase provideAskChampionUseCase(ChampionsRepository repository, GenerativeAiService genAiService) {
		return new AskChampionsUseCase(repository, genAiService);
	}

}
