package com.yusufziyrek.clean_architecture_training.modules.product.domain;

import java.util.List;
import java.util.Optional;

// Dışarıya sözleşme 
public interface ProductRepository {
	List<Product> findAll();

	Optional<Product> findById(Long id);

	void save(Product product);
}
