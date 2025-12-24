package com.yusufziyrek.clean_architecture_training.modules.order.application;

import com.yusufziyrek.clean_architecture_training.modules.order.domain.Order;
import com.yusufziyrek.clean_architecture_training.modules.order.domain.OrderRepository;
import com.yusufziyrek.clean_architecture_training.modules.product.application.ReduceStockUseCase;

public class CreateOrderUseCase {
	private final OrderRepository orderRepository;
	private final ReduceStockUseCase reduceStockUseCase; // Diğer modüle bağımlılık

	public CreateOrderUseCase(OrderRepository orderRepository, ReduceStockUseCase reduceStockUseCase) {
		this.orderRepository = orderRepository;
		this.reduceStockUseCase = reduceStockUseCase;
	}

	public void execute(Long productId, Integer quantity) {
		
		// 1. ADIM: Product modülündeki UseCase'i çağır (Stok düş ve birim fiyatı al)
		Double unitPrice = reduceStockUseCase.execute(productId, quantity);

		// 2. ADIM: Domain kuralını işle (Sipariş nesnesini oluştur)
		Order order = Order.create(productId, quantity, unitPrice);

		// 3. ADIM: Siparişi kendi modülünde kaydet
		orderRepository.save(order);
	}
}