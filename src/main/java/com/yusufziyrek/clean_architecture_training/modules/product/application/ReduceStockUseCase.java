package com.yusufziyrek.clean_architecture_training.modules.product.application;

import com.yusufziyrek.clean_architecture_training.modules.product.domain.Product;
import com.yusufziyrek.clean_architecture_training.modules.product.domain.ProductRepository;

public class ReduceStockUseCase {
	private final ProductRepository productRepository;

	public ReduceStockUseCase(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	public Double execute(Long productId, Integer quantity) {
		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new RuntimeException("Ürün bulunamadı."));

		product.reduceStock(quantity); // Domain kendi iş kuralını uyguluyor
		productRepository.save(product);

		return product.getPrice(); // Sipariş için fiyatı döndür
	}
}
