package com.yusufziyrek.clean_architecture_training.modules.product.presentation.dto;

import com.yusufziyrek.clean_architecture_training.modules.product.domain.Product;

public record ProductResponse(Long id, String name, Double price, Integer stock) {
    public static ProductResponse fromDomain(Product product) {
        return new ProductResponse(product.getId(), product.getName(), product.getPrice(), product.getStock());
    }
}
