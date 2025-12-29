package com.yusufziyrek.clean_architecture_training.modules.product.presentation.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateProductRequest(
        @NotBlank(message = "Product name cannot be empty") String name,
        @NotNull(message = "Price cannot be empty") @Positive(message = "Price must be positive") Double price,
        @NotNull(message = "Stock information is required") @Min(value = 0, message = "Stock cannot be less than 0") Integer stock) {
}
