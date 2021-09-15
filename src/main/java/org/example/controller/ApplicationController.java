package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Controller
@RequestMapping("customers")
public class ApplicationController {
    private final WebClient webClient;

    public ApplicationController(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("http://localhost:8080").build();
    }

    public Mono<String> getMessage() {
        return webClient.get().accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(ApplicationController.class);
    }
}
