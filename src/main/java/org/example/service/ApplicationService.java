package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.entity.Customer;
import org.example.ripository.CustomerRepository;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RequiredArgsConstructor
//@Service
@Component
public class ApplicationService {
    private final CustomerRepository customerRepository;

    public Mono<Customer> create(Customer customer) {
        return customerRepository.save(customer);
    }

    public Flux<Customer> findAll() {
        return customerRepository.findAll();
    }

    public Mono<Customer> findOne(Integer id) {
        return customerRepository.findById(id);
    }

    public Mono<Customer> update(Customer customer) {
        return customerRepository.save(customer);
    }

    public void delete(Integer id) {
        customerRepository.deleteById(id);
    }

}
