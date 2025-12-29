package com.yusufziyrek.clean_architecture_training.modules.product.application;

import com.yusufziyrek.clean_architecture_training.modules.product.domain.Product;
import com.yusufziyrek.clean_architecture_training.modules.product.domain.ProductRepository;

public class CreateProductUseCase {
    private final ProductRepository productRepository;

    public CreateProductUseCase(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product execute(String name, Double price, Integer stock) {
        // İŞ KURALI: Domain nesnesini factory method ile oluştur
        // Validasyonlar Product.create() içinde yapılır
        Product product = Product.create(name, price, stock);

        productRepository.save(product);

        return product;
    }
}
