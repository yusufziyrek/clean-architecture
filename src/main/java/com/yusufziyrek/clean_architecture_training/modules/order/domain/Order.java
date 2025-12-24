package com.yusufziyrek.clean_architecture_training.modules.order.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class Order {
    private final UUID id;
    private final Long productId;
    private final Integer quantity;
    private final Double totalPrice;

    // İş Kuralı: Siparişi oluşturuyoruz.
    public static Order create(Long productId, Integer quantity, Double unitPrice) {
        if (quantity <= 0) throw new RuntimeException("Adet 0'dan büyük olmalı.");
        
        return new Order(
            UUID.randomUUID(), 
            productId, 
            quantity, 
            quantity * unitPrice
        );
    }
}
