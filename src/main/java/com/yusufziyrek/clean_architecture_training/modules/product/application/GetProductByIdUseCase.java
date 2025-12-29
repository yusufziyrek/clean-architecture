package com.yusufziyrek.clean_architecture_training.modules.product.application;

import com.yusufziyrek.clean_architecture_training.modules.product.domain.Product;
import com.yusufziyrek.clean_architecture_training.modules.product.domain.ProductNotFoundException;
import com.yusufziyrek.clean_architecture_training.modules.product.domain.ProductRepository;

public class GetProductByIdUseCase {
    private final ProductRepository productRepository;

    public GetProductByIdUseCase(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product execute(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }
}
