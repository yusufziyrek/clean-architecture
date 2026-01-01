package com.yusufziyrek.clean_architecture_training.modules.product.infrastructure.messaging;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Order Created Event Payload - RabbitMQ'dan gelen mesajın yapısı.
 * 
 * NEDEN AYRI BİR RECORD?
 * - OrderCreatedEvent (domain event) Order modülünde
 * - Bu sınıf Product modülünde, bağımsız tanım
 * - Modüller arası bağımlılığı azaltır
 * - RabbitMQ mesaj formatını temsil eder
 * 
 * NOT: Alan adları OrderCreatedEvent ile aynı olmalı (JSON deserialization
 * için)
 */
public record OrderCreatedEventPayload(
        UUID orderId,
        Long productId,
        Integer quantity,
        LocalDateTime occurredAt) {
}
