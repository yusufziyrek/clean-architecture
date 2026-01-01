package com.yusufziyrek.clean_architecture_training.modules.product.infrastructure.messaging;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.yusufziyrek.clean_architecture_training.infrastructure.messaging.RabbitMQConfig;
import com.yusufziyrek.clean_architecture_training.modules.product.application.ReduceStockUseCase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Order Created Event Listener - RabbitMQ'dan gelen event'leri dinler.
 * 
 * BU SINIF NE YAPAR?
 * 1. RabbitMQ'dan "order.created" event'ini alır (String JSON olarak)
 * 2. JSON'ı parse eder
 * 3. ReduceStockUseCase'i çağırarak stok düşürür
 * 
 * NOT: Outbox pattern'de mesaj String JSON olarak gönderildiği için
 * bu listener String alıp manuel parse ediyor.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class OrderCreatedEventListener {

    private final ReduceStockUseCase reduceStockUseCase;

    // JSON dönüşümü için ObjectMapper
    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    /**
     * Order Created Event Handler
     * 
     * @RabbitListener: Belirtilen kuyruktan mesaj dinler
     *                  Mesaj String olarak gelir (Outbox'tan JSON string
     *                  gönderildiği için)
     */
    @RabbitListener(queues = RabbitMQConfig.STOCK_RESERVE_QUEUE)
    public void consume(String message) {
        log.info("Received message from queue: {}", message);

        try {
            // JSON String'i Java nesnesine çevir
            OrderCreatedEventPayload event = objectMapper.readValue(message, OrderCreatedEventPayload.class);

            log.info("Parsed OrderCreatedEvent: orderId={}, productId={}, quantity={}",
                    event.orderId(), event.productId(), event.quantity());

            // Stok düşür
            reduceStockUseCase.execute(event.productId(), event.quantity());

            log.info("Stock reduced successfully for productId={}", event.productId());

        } catch (Exception e) {
            log.error("Failed to process message: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to process order created event", e);
        }
    }
}
