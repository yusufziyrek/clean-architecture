package com.yusufziyrek.clean_architecture_training.modules.order.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class OrderEntity {
	@Id
	private UUID id;
	private Long productId;
	private Integer quantity;
	private Double totalPrice;
}
