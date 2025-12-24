package com.yusufziyrek.clean_architecture_training.modules.product.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.yusufziyrek.clean_architecture_training.modules.product.application.ReduceStockUseCase;
import com.yusufziyrek.clean_architecture_training.modules.product.domain.ProductRepository;

@Configuration
public class ProductBeanConfig {
	@Bean
	public ReduceStockUseCase reduceStockUseCase(ProductRepository productRepository) {
		return new ReduceStockUseCase(productRepository);
	}
}