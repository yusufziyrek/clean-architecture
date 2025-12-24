package com.yusufziyrek.clean_architecture_training.modules.order.presentation.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yusufziyrek.clean_architecture_training.modules.order.application.CreateOrderUseCase;
import com.yusufziyrek.clean_architecture_training.modules.order.domain.Order;
import com.yusufziyrek.clean_architecture_training.modules.order.presentation.dto.CreateOrderRequest;
import com.yusufziyrek.clean_architecture_training.modules.order.presentation.dto.OrderResponseDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
	private final CreateOrderUseCase createOrderUseCase;

	@PostMapping
    public ResponseEntity<OrderResponseDto> createOrder(@RequestBody CreateOrderRequest request) {
        
		// 1. UseCase'i çalıştır ve Domain Modelini (Order) al
        Order order = createOrderUseCase.execute(request.productId(), request.quantity());

        // 2. Domain -> DTO Mapping
        OrderResponseDto response = new OrderResponseDto(
            order.getId(),
            order.getProductId(),
            order.getQuantity(),
            order.getTotalPrice(),
            "SUCCESS" // Örn: Statik bir durum bilgisi
        );

        // 3. DTO'yu 201 Created koduyla dön
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
