package com.example.sdw24.adapters.in;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sdw24.application.ListChampionsUseCase;
import com.example.sdw24.domain.model.Champions;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Campeões", description = "Endpoints do dominio de Campeões do LOL")
@RestController
@RequestMapping("/champions")
public record ListChampionsRestController(ListChampionsUseCase useCase) {
	
	@CrossOrigin
	@GetMapping
	public List<Champions> findAllChampions() {
		return useCase.findAll();
	}
	
	@CrossOrigin
	@GetMapping("/{id}")
	public Optional<Champions> findChampionById(@PathVariable("id") Long id){
		
		return useCase.findById(id);
	}
	
	
	
}
