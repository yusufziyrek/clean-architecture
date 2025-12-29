package com.yusufziyrek.clean_architecture_training.modules.order.application;

import java.util.UUID;

import com.yusufziyrek.clean_architecture_training.modules.order.domain.Order;
import com.yusufziyrek.clean_architecture_training.modules.order.domain.OrderNotFoundException;
import com.yusufziyrek.clean_architecture_training.modules.order.domain.OrderRepository;

public class GetOrderByIdUseCase {
    private final OrderRepository orderRepository;

    public GetOrderByIdUseCase(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order execute(UUID id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
    }
}
