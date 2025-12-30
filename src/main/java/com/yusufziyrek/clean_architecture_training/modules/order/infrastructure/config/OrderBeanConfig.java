package com.yusufziyrek.clean_architecture_training.modules.order.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.yusufziyrek.clean_architecture_training.common.event.DomainEventPublisher;
import com.yusufziyrek.clean_architecture_training.modules.order.application.CreateOrderUseCase;
import com.yusufziyrek.clean_architecture_training.modules.order.application.GetOrderByIdUseCase;
import com.yusufziyrek.clean_architecture_training.modules.order.domain.OrderRepository;
import com.yusufziyrek.clean_architecture_training.modules.product.domain.ProductRepository;

/**
 * Order Bean Config - Order modülü için Spring Bean tanımları.
 * 
 * DEĞİŞİKLİK:
 * - ReduceStockUseCase bağımlılığı kaldırıldı
 * - DomainEventPublisher eklendi (event yayınlamak için)
 * - ProductRepository eklendi (fiyat okumak için)
 */
@Configuration
public class OrderBeanConfig {

	@Bean
	public CreateOrderUseCase createOrderUseCase(
			OrderRepository orderRepository,
			ProductRepository productRepository,
			DomainEventPublisher eventPublisher) {
		return new CreateOrderUseCase(orderRepository, productRepository, eventPublisher);
	}

	@Bean
	public GetOrderByIdUseCase getOrderByIdUseCase(OrderRepository orderRepository) {
		return new GetOrderByIdUseCase(orderRepository);
	}
}