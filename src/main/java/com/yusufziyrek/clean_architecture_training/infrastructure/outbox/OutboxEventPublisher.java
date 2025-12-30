package com.yusufziyrek.clean_architecture_training.infrastructure.outbox;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.yusufziyrek.clean_architecture_training.common.event.DomainEvent;
import com.yusufziyrek.clean_architecture_training.common.event.DomainEventPublisher;

import lombok.RequiredArgsConstructor;

/**
 * Outbox Event Publisher - DomainEventPublisher'ın ADAPTER implementasyonu.
 * 
 * CLEAN ARCHITECTURE MAPPING:
 * - Port: DomainEventPublisher (interface, common katmanında)
 * - Adapter: OutboxEventPublisher (bu sınıf, infrastructure katmanında)
 * 
 * BU SINIF NE YAPAR?
 * 1. Event'i alır
 * 2. JSON'a çevirir
 * 3. Outbox tablosuna kaydeder
 * 
 * NOT: RabbitMQ'ya göndermez! Scheduler ayrıca gönderir.
 * Bu sayede DB transaction'ı ile atomik olur.
 */
@Component
@RequiredArgsConstructor
public class OutboxEventPublisher implements DomainEventPublisher {

    private final OutboxRepository outboxRepository;

    // JSON dönüşümü için ObjectMapper
    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());

    @Override
    public void publish(DomainEvent event) {
        try {
            // Event'i JSON string'e çevir
            String payload = objectMapper.writeValueAsString(event);

            // Outbox tablosuna kaydet (aynı transaction'da!)
            OutboxMessage message = OutboxMessage.create(
                    event.getEventType(),
                    payload);

            outboxRepository.save(message);

        } catch (JsonProcessingException e) {
            throw new RuntimeException("Event could not be serialized to JSON", e);
        }
    }
}
