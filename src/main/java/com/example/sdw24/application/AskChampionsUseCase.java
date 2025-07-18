package com.example.sdw24.application;


import com.example.sdw24.domain.exception.ChampionNotFoundException;
import com.example.sdw24.domain.model.Champions;
import com.example.sdw24.domain.ports.ChampionsRepository;

public record AskChampionsUseCase(ChampionsRepository repository) {
	public String askChampion( Long championId, String question) {
		Champions champion = repository.findById(championId).orElseThrow(() -> new ChampionNotFoundException(championId));
		
		String championContext = champion.generateContextByQuestion(question);
		
		//TODO: Integrar com IA
		
		return championContext;
	}
}
