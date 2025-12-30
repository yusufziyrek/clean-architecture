package com.yusufziyrek.clean_architecture_training.common.event;

import java.time.LocalDateTime;

/**
 * Domain Event - Tüm domain event'lerinin base interface'i.
 * 
 * NEDEN BU INTERFACE?
 * - Tüm event'lerin ortak bir tip altında toplanması
 * - Event routing için eventType kullanılır
 * - Event tracking için occurredAt kullanılır
 * 
 * Clean Architecture: Bu interface DOMAIN katmanında yaşar,
 * hiçbir framework'e bağımlı değil.
 */
public interface DomainEvent {

    /**
     * Event'in tipi - RabbitMQ routing key olarak kullanılır.
     * Örnek: "order.created", "stock.reduced"
     */
    String getEventType();

    /**
     * Event'in oluştuğu zaman
     */
    LocalDateTime getOccurredAt();
}
