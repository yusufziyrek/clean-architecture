package com.yusufziyrek.clean_architecture_training.infrastructure.outbox;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Outbox Message Entity - Event'leri geçici olarak saklar.
 * 
 * OUTBOX PATTERN NASIL ÇALIŞIR?
 * 1. UseCase bir event publish eder
 * 2. Event bu tabloya kaydedilir (aynı transaction ile)
 * 3. Scheduler bu tablodan okur ve RabbitMQ'ya gönderir
 * 4. Başarılı gönderimden sonra processed=true yapılır
 * 
 * NEDEN BU TABLO?
 * - Order kaydetme ve Event gönderme aynı transaction'da olmalı
 * - RabbitMQ'ya direkt yazarsak, DB commit, MQ fail olabilir (data
 * inconsistency)
 * - Bu tablo "ara istasyon" görevi görür
 */
@Entity
@Table(name = "outbox_messages")
@Getter
@Setter
@NoArgsConstructor
public class OutboxMessage {

    @Id
    private UUID id;

    /**
     * Event tipi - RabbitMQ routing key olarak kullanılır
     * Örnek: "order.created"
     */
    @Column(nullable = false)
    private String eventType;

    /**
     * Event verisi - JSON formatında
     * Örnek: {"orderId": "123", "productId": 1, "quantity": 5}
     */
    @Column(nullable = false, columnDefinition = "TEXT")
    private String payload;

    /**
     * Event oluşturulma zamanı
     */
    @Column(nullable = false)
    private LocalDateTime createdAt;

    /**
     * Event RabbitMQ'ya gönderildi mi?
     * Scheduler bu alanı kontrol eder
     */
    @Column(nullable = false)
    private boolean processed = false;

    /**
     * Factory method - Yeni outbox message oluşturur
     */
    public static OutboxMessage create(String eventType, String payload) {
        OutboxMessage message = new OutboxMessage();
        message.setId(UUID.randomUUID());
        message.setEventType(eventType);
        message.setPayload(payload);
        message.setCreatedAt(LocalDateTime.now());
        message.setProcessed(false);
        return message;
    }
}
