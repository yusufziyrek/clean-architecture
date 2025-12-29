package com.yusufziyrek.clean_architecture_training.modules.order.presentation.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;

import com.yusufziyrek.clean_architecture_training.modules.order.application.CreateOrderUseCase;
import com.yusufziyrek.clean_architecture_training.modules.order.domain.Order;
import com.yusufziyrek.clean_architecture_training.modules.order.presentation.dto.CreateOrderRequest;
import com.yusufziyrek.clean_architecture_training.modules.order.presentation.dto.OrderResponse;

import lombok.RequiredArgsConstructor;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.Valid;
import com.yusufziyrek.clean_architecture_training.modules.order.application.GetOrderByIdUseCase;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Validated
public class OrderController {

    private final CreateOrderUseCase createOrderUseCase;
    private final GetOrderByIdUseCase getOrderByIdUseCase;

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody CreateOrderRequest request) {
        Order order = createOrderUseCase.execute(request.productId(), request.quantity());
        return new ResponseEntity<>(OrderResponse.fromDomain(order), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable @NotNull(message = "ID cannot be null") UUID id) {
        Order order = getOrderByIdUseCase.execute(id);
        return ResponseEntity.ok(OrderResponse.fromDomain(order));
    }
}
