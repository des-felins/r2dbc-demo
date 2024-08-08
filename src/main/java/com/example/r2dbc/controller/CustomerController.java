package com.example.r2dbc.controller;

import com.example.r2dbc.domain.Customer;
import com.example.r2dbc.domain.Order;
import com.example.r2dbc.dto.CustomerDTO;
import com.example.r2dbc.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }


    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<CustomerDTO> findCustomerById(@PathVariable Long id) {
        return customerService.findById(id);

    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Flux<CustomerDTO> findAllCustomers() {
        return customerService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<CustomerDTO> createCustomer(@RequestBody Customer customer) {
        return customerService.createCustomer(customer);
    }

    @PostMapping("/{id}/create-new-order")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Void> createOrderForCustomer(@RequestBody Order order, @PathVariable Long id) {
        return customerService.createOrderForCustomer(order);

    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<CustomerDTO> updateCustomer(@RequestBody Customer customer, @PathVariable Long id) {
        return customerService.updateCustomer(customer);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Void> deleteCustomer(@PathVariable Long id) {
        return customerService.deleteCustomer(id);
    }


}
