package com.spring5.concept.learning.service;

import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring5.concept.learning.model.StringHolder;
import com.spring5.concept.learning.repository.UtilityRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class StringService {

	@Autowired
	UtilityRepository repository;

	public Flux<StringHolder> getTokenizeSentence(int index) {
		Mono<String> sentence = repository.getSentence(index);
		return sentence.flatMapMany(str -> {
			StringTokenizer tokenizer = new StringTokenizer(str);
			int countTokens = tokenizer.countTokens();
			StringHolder[] tokens = new StringHolder[countTokens];
			int i = 0;
			while (tokenizer.hasMoreTokens()) {
				StringHolder holder = new StringHolder();
				holder.setStr(tokenizer.nextToken());
				tokens[i++] = holder;
			}

			return Flux.fromArray(tokens);
		});
	}

	public Mono<StringHolder> toUpperCase(int index) {
		Mono<String> sentence = repository.getSentence(index);
		return sentence.flatMap(str -> {
			StringHolder holder = new StringHolder();
			System.out.println(str);
			holder.setStr(str.toUpperCase());
			return Mono.just(holder);
		});
	}

}
