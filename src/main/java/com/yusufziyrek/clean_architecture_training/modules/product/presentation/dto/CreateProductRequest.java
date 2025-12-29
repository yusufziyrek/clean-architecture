package com.yusufziyrek.clean_architecture_training.modules.product.presentation.dto;

public record CreateProductRequest(String name, Double price, Integer stock) {
}
