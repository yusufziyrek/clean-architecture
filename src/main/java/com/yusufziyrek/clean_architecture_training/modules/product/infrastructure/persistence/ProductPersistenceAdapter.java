package com.yusufziyrek.clean_architecture_training.modules.product.infrastructure.persistence;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.yusufziyrek.clean_architecture_training.modules.product.domain.Product;
import com.yusufziyrek.clean_architecture_training.modules.product.domain.ProductRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProductPersistenceAdapter implements ProductRepository {
	private final JpaProductRepository jpaRepo;

	@Override
	public Optional<Product> findById(Long id) {
		return jpaRepo.findById(id).map(this::mapToDomain);
	}

	@Override
	public void save(Product p) {
		jpaRepo.save(mapToEntity(p));
	}

	// ENTITY -> DOMAIN MAPPING
	private Product mapToDomain(ProductEntity entity) {
		return new Product(entity.getId(), entity.getName(), entity.getPrice(), entity.getStock());
	}

	// DOMAIN -> ENTITY MAPPING
	private ProductEntity mapToEntity(Product product) {
		ProductEntity entity = new ProductEntity();
		entity.setId(product.getId());
		entity.setName(product.getName());
		entity.setPrice(product.getPrice());
		entity.setStock(product.getStock());
		return entity;
	}
}
