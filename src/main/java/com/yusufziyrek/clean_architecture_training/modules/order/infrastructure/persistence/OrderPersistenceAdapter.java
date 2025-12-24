package com.yusufziyrek.clean_architecture_training.modules.order.infrastructure.persistence;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.yusufziyrek.clean_architecture_training.modules.order.domain.Order;
import com.yusufziyrek.clean_architecture_training.modules.order.domain.OrderRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OrderPersistenceAdapter implements OrderRepository {
	private final JpaOrderRepository jpaRepo;
    
    public Optional<Order> findById(UUID id) {
        return jpaRepo.findById(id).map(this::mapToDomain);
    }
    
    @Override
    public void save(Order order) {
        // Domain -> Entity dönüşümü ve kayıt
        OrderEntity entity = mapToEntity(order);
        jpaRepo.save(entity);
    }
	
	// Domain Modelini Veritabanı Entity'sine çevirir
    private OrderEntity mapToEntity(Order order) {
        OrderEntity entity = new OrderEntity();
        entity.setId(order.getId());
        entity.setProductId(order.getProductId());
        entity.setQuantity(order.getQuantity());
        entity.setTotalPrice(order.getTotalPrice());
        return entity;
    }

    // Veritabanı Entity'sini Domain Modeline çevirir
    private Order mapToDomain(OrderEntity entity) {
        return new Order(
            entity.getId(),
            entity.getProductId(),
            entity.getQuantity(),
            entity.getTotalPrice()
        );
    }

}
