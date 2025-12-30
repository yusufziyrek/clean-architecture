package com.yusufziyrek.clean_architecture_training.infrastructure.outbox;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Outbox Repository - Outbox tablosuna erişim için JPA Repository.
 * 
 * SCHEDULER NASIL KULLANIR?
 * 1. findByProcessedFalse() ile bekleyen event'leri alır
 * 2. Her birini RabbitMQ'ya gönderir
 * 3. save() ile processed=true yapar
 */
@Repository
public interface OutboxRepository extends JpaRepository<OutboxMessage, UUID> {

    /**
     * Henüz işlenmemiş (gönderilmemiş) event'leri bulur.
     * Scheduler bu metodu kullanır.
     */
    List<OutboxMessage> findByProcessedFalse();
}
