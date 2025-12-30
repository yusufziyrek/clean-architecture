package com.yusufziyrek.clean_architecture_training.modules.order.application;

import java.time.LocalDateTime;

import com.yusufziyrek.clean_architecture_training.common.event.DomainEventPublisher;
import com.yusufziyrek.clean_architecture_training.modules.order.domain.Order;
import com.yusufziyrek.clean_architecture_training.modules.order.domain.OrderRepository;
import com.yusufziyrek.clean_architecture_training.modules.order.domain.event.OrderCreatedEvent;
import com.yusufziyrek.clean_architecture_training.modules.product.domain.Product;
import com.yusufziyrek.clean_architecture_training.modules.product.domain.ProductNotFoundException;
import com.yusufziyrek.clean_architecture_training.modules.product.domain.ProductRepository;

/**
 * Create Order Use Case - Sipariş oluşturma iş akışı.
 * 
 * ÖNCEKİ DURUM (Senkron, Tight Coupling):
 * - ReduceStockUseCase direkt çağrılıyordu
 * - Order modülü → Product modülü bağımlılığı vardı
 * - Test yazmak zordu (mock'lamak gerekiyordu)
 * 
 * YENİ DURUM (Asenkron, Event-Driven):
 * - OrderCreatedEvent yayınlanır
 * - Product modülü bu event'i dinler ve stok düşürür
 * - Modüller birbirinden bağımsız çalışır
 * 
 * NOT: ProductRepository hala import ediliyor çünkü fiyat bilgisine ihtiyacımız
 * var.
 * Bu sadece READ işlemi, modüller arası bağımlılık açısından kabul edilebilir.
 */
public class CreateOrderUseCase {
	private final OrderRepository orderRepository;
	private final ProductRepository productRepository; // Fiyat okumak için
	private final DomainEventPublisher eventPublisher; // Event yayınlamak için

	public CreateOrderUseCase(
			OrderRepository orderRepository,
			ProductRepository productRepository,
			DomainEventPublisher eventPublisher) {
		this.orderRepository = orderRepository;
		this.productRepository = productRepository;
		this.eventPublisher = eventPublisher;
	}

	public Order execute(Long productId, Integer quantity) {

		// 1. ADIM: Ürün fiyatını oku (sadece okuma, stok düşürme YOK)
		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new ProductNotFoundException(productId));
		Double unitPrice = product.getPrice();

		// 2. ADIM: Siparişi oluştur ve kaydet
		Order order = Order.create(productId, quantity, unitPrice);
		orderRepository.save(order);

		// 3. ADIM: Event yayınla (Outbox tablosuna kaydedilir)
		// Product modülü bu event'i dinleyip stok düşürecek
		eventPublisher.publish(new OrderCreatedEvent(
				order.getId(),
				productId,
				quantity,
				LocalDateTime.now()));

		return order;
	}
}