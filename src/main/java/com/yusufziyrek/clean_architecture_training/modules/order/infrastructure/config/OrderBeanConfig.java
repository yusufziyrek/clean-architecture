package com.yusufziyrek.clean_architecture_training.modules.order.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.yusufziyrek.clean_architecture_training.modules.order.application.CreateOrderUseCase;
import com.yusufziyrek.clean_architecture_training.modules.order.domain.OrderRepository;
import com.yusufziyrek.clean_architecture_training.modules.product.application.ReduceStockUseCase;

@Configuration
public class OrderBeanConfig {
	@Bean
	public CreateOrderUseCase createOrderUseCase(OrderRepository orderRepository,
			ReduceStockUseCase reduceStockUseCase) { // Product UseCase'ini enjekte ediyoruz
		return new CreateOrderUseCase(orderRepository, reduceStockUseCase);
	}
}