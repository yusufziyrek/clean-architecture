package com.yusufziyrek.clean_architecture_training.infrastructure.outbox;

import java.util.List;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.yusufziyrek.clean_architecture_training.infrastructure.messaging.RabbitMQConfig;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Outbox Message Relay - Outbox tablosundaki event'leri RabbitMQ'ya gönderir.
 * 
 * OUTBOX PATTERN'ın SON ADIMI:
 * 1. CreateOrderUseCase → Event'i Outbox tablosuna yazar
 * 2. Bu scheduler → Outbox'tan okur → RabbitMQ'ya gönderir → processed=true
 * yapar
 * 
 * NEDEN SCHEDULER?
 * - Mesaj gönderimi asenkron olmalı
 * - Retry mekanizması sağlar (başarısızsa sonraki çalışmada tekrar dener)
 * - Database transaction'ından bağımsız çalışır
 * 
 * @Scheduled: Spring'in zamanlayıcı anotasyonu
 *             - fixedDelay: Her çalışmadan sonra X ms bekle
 *             - Son çalışma bittikten 5 saniye sonra tekrar çalışır
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class OutboxMessageRelay {

    private final OutboxRepository outboxRepository;
    private final RabbitTemplate rabbitTemplate;

    /**
     * Her 5 saniyede bir çalışır ve bekleyen event'leri RabbitMQ'ya gönderir.
     * 
     * AKIŞ:
     * 1. processed=false olan mesajları bul
     * 2. Her mesajı RabbitMQ'ya gönder
     * 3. Başarılıysa processed=true yap
     * 4. Hata olursa log'la ve sonraki çalışmada tekrar dene
     */
    @Scheduled(fixedDelay = 5000) // 5 saniye
    @Transactional
    public void processOutbox() {
        List<OutboxMessage> pendingMessages = outboxRepository.findByProcessedFalse();

        if (pendingMessages.isEmpty()) {
            return; // Bekleyen mesaj yok
        }

        log.info("Processing {} outbox messages...", pendingMessages.size());

        for (OutboxMessage message : pendingMessages) {
            try {
                // RabbitMQ'ya gönder
                rabbitTemplate.convertAndSend(
                        RabbitMQConfig.ORDER_EVENTS_EXCHANGE, // Exchange
                        message.getEventType(), // Routing key (örn: "order.created")
                        message.getPayload() // Mesaj içeriği (JSON)
                );

                // Başarılı gönderim - processed olarak işaretle
                message.setProcessed(true);
                outboxRepository.save(message);

                log.info("Outbox message sent: id={}, type={}",
                        message.getId(), message.getEventType());

            } catch (Exception e) {
                // Hata durumunda log'la, sonraki çalışmada tekrar denenecek
                log.error("Failed to send outbox message: id={}, error={}",
                        message.getId(), e.getMessage());
                // processed=false kalacak, retry edilecek
            }
        }
    }
}
