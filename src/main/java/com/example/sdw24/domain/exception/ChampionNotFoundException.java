package com.example.sdw24.domain.exception;

public class ChampionNotFoundException extends RuntimeException {

	public ChampionNotFoundException(Long championId) {
		super("Champion %d not found.".formatted(championId));
	}
}
