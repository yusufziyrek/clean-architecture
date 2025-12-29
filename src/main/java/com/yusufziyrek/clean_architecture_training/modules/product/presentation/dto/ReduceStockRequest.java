package com.yusufziyrek.clean_architecture_training.modules.product.presentation.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ReduceStockRequest(
        @NotNull(message = "Quantity cannot be empty") @Positive(message = "Quantity must be positive") Integer quantity) {
}
