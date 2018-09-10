package com.spring5.concept.learning.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.spring5.concept.learning.model.IntegerHolder;
import com.spring5.concept.learning.model.StringHolder;
import com.spring5.concept.learning.service.MathService;
import com.spring5.concept.learning.service.StringService;

import reactor.core.publisher.Mono;

@Component
public class UtilityHandler {
	
	@Autowired
	StringService stringService;
	
	@Autowired
	MathService mathService;
	
	public Mono<ServerResponse> getTokenizeSentence(ServerRequest request){
		return ServerResponse
				.ok()
				.body(
						stringService.getTokenizeSentence(
								Integer.parseInt(request.queryParam("index").get())
								),
						StringHolder.class
				);
	}
	
	public Mono<ServerResponse> toUpperCase(ServerRequest request){
		return ServerResponse
				.ok()
				.body(
						stringService.toUpperCase(
								Integer.parseInt(request.queryParam("index").get())
								),
						StringHolder.class
				);
	}
	
	public Mono<ServerResponse> find(ServerRequest request){
		return ServerResponse
				.ok()
				.body(
						mathService.find(request.pathVariable("method")),
						IntegerHolder.class
				);
	}

}
