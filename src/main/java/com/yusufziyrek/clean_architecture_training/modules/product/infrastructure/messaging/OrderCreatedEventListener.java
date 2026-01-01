package com.yusufziyrek.clean_architecture_training.modules.product.infrastructure.messaging;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.yusufziyrek.clean_architecture_training.infrastructure.messaging.RabbitMQConfig;
import com.yusufziyrek.clean_architecture_training.modules.product.application.ReduceStockUseCase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Order Created Event Listener - RabbitMQ'dan gelen event'leri dinler.
 * 
 * BU SINIF NE YAPAR?
 * 1. RabbitMQ'dan "order.created" event'ini alır
 * 2. ReduceStockUseCase'i çağırarak stok düşürür
 * 
 * RABBITMQ LISTENER NASIL ÇALIŞIR?
 * - @RabbitListener anotasyonu ile kuyruk dinlenir
 * - Mesaj geldiğinde otomatik olarak consume() metodu çağrılır
 * - Mesaj JSON'dan Java nesnesine dönüştürülür (Jackson)
 * 
 * ASENKRON İŞLEM:
 * - Order modülü event yayınladı ve işini bitirdi
 * - Bu listener ayrı bir thread'de çalışır
 * - Order yanıtı beklemeden Product stok işlemini yapar
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class OrderCreatedEventListener {

    private final ReduceStockUseCase reduceStockUseCase;

    /**
     * Order Created Event Handler
     * 
     * @RabbitListener: Belirtilen kuyruktan mesaj dinler
     *                  Bu metod her yeni mesaj geldiğinde otomatik çağrılır
     */
    @RabbitListener(queues = RabbitMQConfig.STOCK_RESERVE_QUEUE)
    public void consume(OrderCreatedEventPayload event) {
        log.info("📦 Received OrderCreatedEvent: orderId={}, productId={}, quantity={}",
                event.orderId(), event.productId(), event.quantity());

        try {
            // Stok düşür
            reduceStockUseCase.execute(event.productId(), event.quantity());

            log.info("✅ Stock reduced successfully for productId={}", event.productId());

        } catch (Exception e) {
            log.error("❌ Failed to reduce stock for productId={}: {}",
                    event.productId(), e.getMessage());
            // TODO: Dead Letter Queue veya retry mekanizması eklenebilir
            throw e; // Mesaj tekrar kuyruğa alınsın
        }
    }
}
