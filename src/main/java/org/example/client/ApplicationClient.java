package org.example.client;

import lombok.RequiredArgsConstructor;
import org.example.entity.Customer;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Component
@RequiredArgsConstructor
public class ApplicationClient {
    private final WebClient webClient;

    public ApplicationClient(WebClient.Builder builder) {
        webClient = builder.baseUrl("http://localhost:8080").build();
    }

    public Flux<Customer> customersList() {
       return webClient.get().uri("customers/list").accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(Customer.class);
    }

}

