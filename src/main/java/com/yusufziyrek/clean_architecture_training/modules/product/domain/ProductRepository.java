package com.yusufziyrek.clean_architecture_training.modules.product.domain;

import java.util.Optional;

// Dışarıya sözleşme 
public interface ProductRepository {
	Optional<Product> findById(Long id);
	void save(Product product);
}
