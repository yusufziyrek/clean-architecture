package com.yusufziyrek.clean_architecture_training.modules.order.presentation.dto;

public record CreateOrderRequest(Long productId, Integer quantity) {
}
