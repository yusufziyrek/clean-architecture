package com.yusufziyrek.clean_architecture_training.modules.order.presentation.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yusufziyrek.clean_architecture_training.modules.order.application.CreateOrderUseCase;
import com.yusufziyrek.clean_architecture_training.modules.order.presentation.dto.CreateOrderRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
	private final CreateOrderUseCase createOrderUseCase;

	@PostMapping
	public ResponseEntity<String> createOrder(@RequestBody CreateOrderRequest request) {
		createOrderUseCase.execute(request.productId(), request.quantity());
		return ResponseEntity.ok("Sipariş başarıyla oluşturuldu.");
	}
}
