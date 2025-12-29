package com.yusufziyrek.clean_architecture_training.modules.order.domain;

import lombok.Getter;
import java.util.UUID;

@Getter
public class Order {
    private final UUID id;
    private final Long productId;
    private final Integer quantity;
    private final Double totalPrice;

    // Private constructor - Dışarıdan doğrudan new Order() yapılamaz
    private Order(UUID id, Long productId, Integer quantity, Double totalPrice) {
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    // FACTORY METHOD: Yeni sipariş oluşturma (İş kuralları burada doğrulanır)
    public static Order create(Long productId, Integer quantity, Double unitPrice) {
        // İŞ KURALI: Sipariş adedi en az 1 olmalı
        if (quantity == null || quantity <= 0) {
            throw new InvalidOrderQuantityException(quantity);
        }

        return new Order(
                UUID.randomUUID(),
                productId,
                quantity,
                quantity * unitPrice);
    }

    // RECONSTITUTE: Veritabanından gelen veriyi domain modeline çevirir
    // Validasyon yapılmaz çünkü veri zaten kayıtlı ve geçerli kabul edilir
    public static Order reconstitute(UUID id, Long productId, Integer quantity, Double totalPrice) {
        return new Order(id, productId, quantity, totalPrice);
    }
}
