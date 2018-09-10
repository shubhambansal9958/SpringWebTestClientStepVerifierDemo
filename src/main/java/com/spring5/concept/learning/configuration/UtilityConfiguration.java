package com.spring5.concept.learning.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.spring5.concept.learning.handler.UtilityHandler;


@Configuration
public class UtilityConfiguration {

	@Bean
	public RouterFunction<ServerResponse> routes(UtilityHandler handler) {
		return RouterFunctions
				.route(GET("/utility/getTokenizeSentence"), handler::getTokenizeSentence)
				.andRoute(GET("/utility/toUpperCase"), handler::toUpperCase)
				.andRoute(GET("/utility/find/{method}"), handler::find)
				;
	}
}
