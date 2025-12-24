package com.yusufziyrek.clean_architecture_training.modules.product.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Product {
	private final Long id;
	private final String name;
	private final Double price;
	private Integer stock;

	// İŞ KURALI: Ürün kendi stoğunu yönetir.
	// Dışarıdaki bir servis bu if'i yazamaz, bu nesneye sormalıdır.
	public void reduceStock(int quantity) {
		if (this.stock < quantity) {
			throw new RuntimeException("Stok yetersiz! Mevcut: " + this.stock);
		}
		this.stock -= quantity;
	}
}
