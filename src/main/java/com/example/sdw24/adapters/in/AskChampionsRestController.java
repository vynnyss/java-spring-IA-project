package com.example.sdw24.adapters.in;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sdw24.application.AskChampionsUseCase;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Campeões", description = "Endpoints do dominio de Campeões do LOL")
@RestController
@RequestMapping("/champions")
public record AskChampionsRestController(AskChampionsUseCase useCase) {
	
	
	@PostMapping("/{id}/ask")
	public AskChampionResponse askChampion(
			@PathVariable("id") Long id,
			@RequestBody AskChampionRequest request){
		
		String answer = useCase.askChampion(id, request.question());
		
		return new AskChampionResponse(answer);
	}
	
	public record AskChampionRequest(String question) {}
	public record AskChampionResponse(String answer) {}
}
