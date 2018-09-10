package com.spring5.concept.learning.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring5.concept.learning.model.IntegerHolder;
import com.spring5.concept.learning.repository.UtilityRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MathService {

	@Autowired
	UtilityRepository repository;

	public Mono<IntegerHolder> find(String method) {

		Mono<List<Integer>> monoList = repository.getIntegerData().collectList();

		return monoList.flatMap(list -> {
			int temp=0;
			if (method.equals("largest")) {
				temp = Integer.MIN_VALUE;
				for (int i : list) {
					if (i > temp)
						temp = i;
				}
			} else if(method.equals("smallest")) {
				temp = Integer.MAX_VALUE;
				for (int i : list) {
					if (i < temp)
						temp = i;
				}
			} else {
				return Mono.empty();
			}

			IntegerHolder holder = new IntegerHolder();
			holder.setNumber(temp);

			return Mono.just(holder);
		});

	}

}
