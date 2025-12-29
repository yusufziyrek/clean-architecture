package com.yusufziyrek.clean_architecture_training.modules.order.presentation.dto;

import com.yusufziyrek.clean_architecture_training.modules.order.domain.Order;
import java.util.UUID;

public record OrderResponse(
        UUID id,
        Long productId,
        Integer quantity,
        Double totalPrice
) {
    public static OrderResponse fromDomain(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getProductId(),
                order.getQuantity(),
                order.getTotalPrice()
        );
    }
}
