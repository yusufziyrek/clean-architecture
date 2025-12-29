package com.yusufziyrek.clean_architecture_training.modules.product.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.yusufziyrek.clean_architecture_training.modules.product.application.CreateProductUseCase;
import com.yusufziyrek.clean_architecture_training.modules.product.application.GetProductByIdUseCase;
import com.yusufziyrek.clean_architecture_training.modules.product.application.ListProductsUseCase;
import com.yusufziyrek.clean_architecture_training.modules.product.application.ReduceStockUseCase;
import com.yusufziyrek.clean_architecture_training.modules.product.domain.ProductRepository;

@Configuration
public class ProductBeanConfig {
	@Bean
	public ReduceStockUseCase reduceStockUseCase(ProductRepository productRepository) {
		return new ReduceStockUseCase(productRepository);
	}

	@Bean
	public CreateProductUseCase createProductUseCase(
			ProductRepository productRepository) {
		return new CreateProductUseCase(productRepository);
	}

	@Bean
	public GetProductByIdUseCase getProductByIdUseCase(
			ProductRepository productRepository) {
		return new GetProductByIdUseCase(productRepository);
	}

	@Bean
	public ListProductsUseCase listProductsUseCase(
			ProductRepository productRepository) {
		return new ListProductsUseCase(productRepository);
	}
}