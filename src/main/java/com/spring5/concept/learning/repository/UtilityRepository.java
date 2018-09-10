package com.spring5.concept.learning.repository;

import org.springframework.stereotype.Repository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class UtilityRepository {
	
	Flux<String> sentences = Flux.just(
			"I am a sentence come from utility repo",
			"This outcome is result of wok done by Shubham",
			"You're accessing the Utility Repo"
			);
	
	Flux<Integer> numbers = Flux.just(12,54,67,7,90);

	public Mono<String> getSentence(int index) {
		return sentences.elementAt(index);
	}
	
	public Flux<Integer> getIntegerData(){
		return numbers;
	}
}
