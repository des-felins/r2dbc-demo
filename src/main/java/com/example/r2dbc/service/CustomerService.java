package com.example.r2dbc.service;

import com.example.r2dbc.domain.Customer;
import com.example.r2dbc.domain.Order;
import com.example.r2dbc.dto.CustomerDTO;
import com.example.r2dbc.repository.CustomerRepository;
import com.example.r2dbc.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.util.NoSuchElementException;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;

    public CustomerService(CustomerRepository customerRepository, OrderRepository orderRepository) {
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
    }


    public Mono<CustomerDTO> findById(Long id) {

        return customerRepository.findById(id)
                .switchIfEmpty(Mono.error(new NoSuchElementException()))
                .flatMap(this::getOrdersForCustomer);
    }

    public Flux<CustomerDTO> findAll() {
        return customerRepository.findAll()
                .flatMap(this::getOrdersForCustomer);
    }

    private Mono<CustomerDTO> getOrdersForCustomer(Customer customer) {

        return Mono.just(customer)
                .zipWith(orderRepository.findAllByCustomerId(customer.getId()).collectList())
                .map(result -> new CustomerDTO(
                        result.getT1().getId(),
                        result.getT1().getName(),
                        result.getT1().getEmail(),
                        result.getT2()));

    }

    @Transactional
    public Mono<CustomerDTO> createCustomer(Customer customer) {
        if (customer.getId() != null) {
            return Mono.error(new IllegalArgumentException());
        }

        return customerRepository.save(customer)
                .map(newCustomer -> new CustomerDTO(
                        newCustomer.getId(),
                        newCustomer.getName(),
                        newCustomer.getEmail(),
                        newCustomer.getOrders()));

    }

    @Transactional
    public Mono<CustomerDTO> updateCustomer(Customer customer) {
        return customerExists(customer.getId())
                .then(customerRepository.save(customer))
                .map(newCustomer -> new CustomerDTO(
                        newCustomer.getId(),
                        newCustomer.getName(),
                        newCustomer.getEmail(),
                        newCustomer.getOrders()));

    }

    @Transactional
    public Mono<Void> deleteCustomer(Long id) {

        orderRepository.deleteAllByCustomerId(id);
        return customerRepository.deleteById(id);


        /*

        This one deletes orders, but not a customer!

                return customerRepository.findById(id)
                .zipWith(orderRepository.deleteAllByCustomerId(id))
                .map(Tuple2::getT1)
                .flatMap(customerRepository::delete);

         */
    }


    @Transactional
    public Mono<Void> createOrderForCustomer(Order order) {
        if (order.getId() != null) {
            return Mono.error(new IllegalArgumentException());
        }

        return customerExists(order.getCustomerId())
                .then(orderRepository.save(order)).then();

    }

    private Mono<Boolean> customerExists(Long id) {
        return customerRepository.existsById(id).handle((exists, sink) -> {
            if (Boolean.FALSE.equals(exists)) {
                sink.error(new IllegalArgumentException());
            } else {
                sink.next(exists);
            }
        });
    }


}
