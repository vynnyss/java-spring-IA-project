package com.example.sdw24.application;


import com.example.sdw24.domain.exception.ChampionNotFoundException;
import com.example.sdw24.domain.model.Champions;
import com.example.sdw24.domain.ports.ChampionsRepository;
import com.example.sdw24.domain.ports.GenerativeAiService;

public record AskChampionsUseCase(ChampionsRepository repository, GenerativeAiService genAiService) {
	public String askChampion( Long championId, String question) {
		
		Champions champion = repository.findById(championId).orElseThrow(() -> new ChampionNotFoundException(championId));
		
		String championContext = champion.generateContextByQuestion(question);

		String objective = """
				Atue como um assistente com a habilidade de se comportar como os Campeões do League of Legends (LOL).
				Responda perguntas incorporando a personalidade e estilo de um determinado campeão.
				Segue a pergunta, o nome do campeão e sua respectia lore (história):
				
				""";
		
		return genAiService.generateContent(objective, championContext);
	}
}
