package com.yusufziyrek.clean_architecture_training.modules.order.domain.event;

import java.time.LocalDateTime;
import java.util.UUID;

import com.yusufziyrek.clean_architecture_training.common.event.DomainEvent;

/**
 * Order Created Event - Sipariş oluşturulduğunda yayınlanan domain event.
 * 
 * BU EVENT NE İÇİN?
 * - Order modülü sipariş oluşturduğunda bu event'i yayınlar
 * - Product modülü bu event'i dinler ve stok düşürür
 * - Modüller arası asenkron iletişim sağlar
 * 
 * NEDEN RECORD?
 * - Immutable (değiştirilemez)
 * - Otomatik equals, hashCode, toString
 * - Serialization için ideal
 */
public record OrderCreatedEvent(
        UUID orderId,
        Long productId,
        Integer quantity,
        LocalDateTime occurredAt) implements DomainEvent {

    /**
     * Event tipi - RabbitMQ routing key olarak kullanılır
     */
    @Override
    public String getEventType() {
        return "order.created";
    }

    /**
     * Event oluşturma zamanı
     */
    @Override
    public LocalDateTime getOccurredAt() {
        return occurredAt;
    }
}
