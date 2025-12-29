package com.yusufziyrek.clean_architecture_training.modules.product.domain;

import lombok.Getter;

@Getter
public class Product {
	private final Long id;
	private final String name;
	private final Double price;
	private Integer stock;

	// Private constructor - Dışarıdan doğrudan new Product() yapılamaz
	private Product(Long id, String name, Double price, Integer stock) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.stock = stock;
	}

	// FACTORY METHOD: Yeni ürün oluşturma (İş kuralları burada doğrulanır)
	public static Product create(String name, Double price, Integer stock) {
		// İŞ KURALI 1: Ürün adı boş olamaz
		if (name == null || name.isBlank()) {
			throw new InvalidProductNameException();
		}

		// İŞ KURALI 2: Fiyat pozitif olmalı
		if (price == null || price <= 0) {
			throw new InvalidProductPriceException(price);
		}

		// İŞ KURALI 3: Stok negatif olamaz
		if (stock == null || stock < 0) {
			throw new InvalidProductStockException(stock);
		}

		// ID null çünkü veritabanı oluşturacak (Auto Increment)
		return new Product(null, name, price, stock);
	}

	// RECONSTITUTE: Veritabanından gelen veriyi domain modeline çevirir
	// Validasyon yapılmaz çünkü veri zaten kayıtlı ve geçerli kabul edilir
	public static Product reconstitute(Long id, String name, Double price, Integer stock) {
		return new Product(id, name, price, stock);
	}

	// İŞ KURALI: Ürün kendi stoğunu yönetir.
	// Dışarıdaki bir servis bu if'i yazamaz, bunu nesneye sormalıdır.
	public void reduceStock(int quantity) {
		if (this.stock < quantity) {
			throw new InsufficientStockException(this.name);
		}
		this.stock -= quantity;
	}
}
