package com.example.sdw24.application;

import java.util.List;
import java.util.Optional;

import com.example.sdw24.domain.model.Champions;
import com.example.sdw24.domain.ports.ChampionsRepository;

public record ListChampionsUseCase(ChampionsRepository repository) {
	public List<Champions> findAll(){
		return repository.findAll();
	}
	
	public Optional<Champions> findById(Long id){
		return repository.findById(id);
	}
	
	
}
