package com.yusufziyrek.clean_architecture_training.common.event;

/**
 * Domain Event Publisher - Event yayınlamak için PORT (interface).
 * 
 * NEDEN BU INTERFACE?
 * - Clean Architecture: Domain/Application katmanı RabbitMQ'yu bilmemeli
 * - Bu interface bir "sözleşme" (contract)
 * - Infrastructure katmanında RabbitMQ/Outbox adapter'ı implement edecek
 * 
 * Dependency Inversion Prensibi:
 * - UseCase → DomainEventPublisher (interface) ← OutboxEventPublisher (impl)
 * - UseCase asla OutboxEventPublisher'ı direkt bilmez
 */
public interface DomainEventPublisher {

    /**
     * Event'i yayınla.
     * 
     * Gerçek implementasyon (OutboxEventPublisher):
     * - Event'i Outbox tablosuna kaydedecek
     * - Scheduler ayrıca RabbitMQ'ya gönderecek
     */
    void publish(DomainEvent event);
}
