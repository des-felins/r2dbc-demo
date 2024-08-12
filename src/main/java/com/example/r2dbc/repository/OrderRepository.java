package com.example.r2dbc.repository;

import com.example.r2dbc.domain.Order;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface OrderRepository extends ReactiveCrudRepository<Order, Long>  {

    Flux<Order> findAllByCustomerId(Long customerId);
    Mono<Long> deleteAllByCustomerId(Long customerId);

}
