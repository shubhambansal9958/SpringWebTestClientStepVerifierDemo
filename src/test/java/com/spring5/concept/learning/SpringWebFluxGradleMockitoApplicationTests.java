package com.spring5.concept.learning;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.util.HashMap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.spring5.concept.learning.model.IntegerHolder;
import com.spring5.concept.learning.model.StringHolder;
import com.spring5.concept.learning.repository.UtilityRepository;
import com.spring5.concept.learning.service.MathService;
import com.spring5.concept.learning.service.StringService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SpringWebFluxGradleMockitoApplicationTests {

	@MockBean
	UtilityRepository repository;

	@Autowired
	StringService stringService;
	
	@Autowired
	MathService mathService;
	
	@Autowired
	RouterFunction<ServerResponse> router;
	
	@Autowired
	ApplicationContext context;

	@Test
	public void test_repository() {

		// given
		String str = "I am a Testing sentence";
		Mono<String> mono = Mono.just(str);

		// when
		when(repository.getSentence(anyInt())).thenReturn(mono);

		// then
		StepVerifier.create(repository.getSentence(0))
			.expectNext(str)
			.expectComplete()
			.verify();

	}

	@Test
	public void test_service() {

		// given
		String str = "I am a Testing sentence";
		Mono<String> mono = Mono.just(str);
		StringHolder holder = new StringHolder();
		holder.setStr(str.toUpperCase());

		// when
		when(repository.getSentence(anyInt())).thenReturn(mono);

		// then
		StepVerifier.create(stringService.toUpperCase(0))
			.expectNext(holder)
			.expectComplete()
			.verify();
	}

//	@Test
//	public void test_handler() {
//
//		// given
//		String str = "I am a Testing sentence";
//		Mono<String> mono = Mono.just(str);	
//			
//		HandlerFunction<ServerResponse> handlerFunction = handler::toUpperCase;
//		RouterFunction<ServerResponse> routerFunction = req -> Mono.just(handlerFunction);	

//		
//		MockServerRequest request = MockServerRequest
//				.builder()
//				.method(HttpMethod.GET)
//				.uri(URI.create("http://localhost:8080/utility/toUpperCase"))
//				.build();
//		
//		Mono<HandlerFunction<ServerResponse>> result = routerFunction.route(request);
//		
//		// when
//		//when(request.queryParam("index").get()).thenReturn("1");
//		when(repository.getSentence(anyInt())).thenReturn(mono);
//
//		// then		
//		StepVerifier.create(result)
//			.expectNext(handlerFunction)
//			.expectComplete().verify();

//	HandlerFunction<ServerResponse> h = new HandlerFunction<ServerResponse>() {
//
//		@Override
//		public Mono<ServerResponse> handle(ServerRequest request) {
//			// TODO Auto-generated method stub
//			return handler.toUpperCase(request);
//		}
//	};

//	
//	
//	RouterFunction<ServerResponse> r = new RouterFunction<ServerResponse>() {
//
//		@Override
//		public Mono<HandlerFunction<ServerResponse>> route(ServerRequest request) {
//			// TODO Auto-generated method stub
//			return Mono.just(h);
//		}
//	};

//	HandlerFunction<ServerResponse> h = req -> handler.toUpperCase(req);
//	RouterFunction<ServerResponse> r = req -> Mono.just(h);
//
//	}

	@Test
	public void test_router() {

		// given
		String str = "I am a Testing sentence";
		Mono<String> mono = Mono.just(str);
		StringHolder holder = new StringHolder();
		holder.setStr(str.toUpperCase());

		WebTestClient webTestClient = WebTestClient.bindToRouterFunction(router)
										.build();
		
		// when
		when(repository.getSentence(anyInt())).thenReturn(mono);

		// then
		webTestClient.get()
			.uri(uriBuilder -> uriBuilder.path("/utility/toUpperCase").queryParam("index", "3").build() )
			.exchange()
			.expectStatus().isOk()
			.expectBody(StringHolder.class).isEqualTo(holder);
	}
	
	@Test
	public void test_context() {
		
		// given
		Flux<Integer> numbers = Flux.just(5,34,23,98,12);
		
		IntegerHolder holder = new IntegerHolder();
		holder.setNumber(5);
		
		WebTestClient webTestClient = WebTestClient.bindToApplicationContext(context)
				.build();
		
		HashMap<String, String> hashMap = new HashMap<>();

		// when
		when(repository.getIntegerData()).thenReturn(numbers);
		hashMap.put("method", "smallest");

		// then		
		webTestClient.get()
			.uri("/utility/find/{method}", hashMap)
			.exchange()
			.expectStatus().isOk()
			.expectBody(IntegerHolder.class).isEqualTo(holder);		
	}
	
	@Test
	public void test_server() {
		
		//given
		WebTestClient webTestClient = WebTestClient.bindToServer()
										.baseUrl("http://localhost:8080")
										.build();
		
		String uri = "/utility/getTokenizeSentence";
		
		//then
		Flux<StringHolder> response = webTestClient.get()
			.uri(uriBuilder->uriBuilder.path(uri).queryParam("index", "1").build())
			.exchange()
			.expectStatus().isOk()
			.returnResult(StringHolder.class).getResponseBody()
			;
		
		StepVerifier.create(response)
				.expectNext(new StringHolder("This")) 
				.expectNext(new StringHolder("outcome")) 
				.expectNext(new StringHolder("is")) 
				.expectNext(new StringHolder("result")) 
				.expectNext(new StringHolder("of")) 
				.expectNext(new StringHolder("work")) 
				.expectNext(new StringHolder("done"))
				.expectNext(new StringHolder("by"))
				.expectNext(new StringHolder("Shubham"))
				.expectComplete()
				.verify()
				;
		
		
	}

}
