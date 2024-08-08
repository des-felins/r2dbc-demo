package com.example.r2dbc.dto;

import com.example.r2dbc.domain.Order;
import org.springframework.data.annotation.Transient;

import java.util.List;

public record CustomerDTO (Long id,
                           String name,
                           String email,
                           List<Order> orders) {
}
