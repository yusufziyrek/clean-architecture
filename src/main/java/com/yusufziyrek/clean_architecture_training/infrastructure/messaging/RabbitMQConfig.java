package com.yusufziyrek.clean_architecture_training.infrastructure.messaging;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// JacksonJsonMessageConverter uses Jackson 3.x internally with sensible defaults

/**
 * RabbitMQ Configuration - Exchange, Queue ve Binding tanımları.
 * 
 * RABBITMQ KAVRAMLARI:
 * 
 * 1. EXCHANGE: Mesajları kuyruklara yönlendiren "postane"
 * - DirectExchange: Routing key tam eşleşirse kuyruğa gönderir
 * 
 * 2. QUEUE: Mesajların beklediği kuyruk
 * - Consumer'lar bu kuyruktan mesaj alır
 * 
 * 3. BINDING: Exchange ile Queue arasındaki bağlantı kuralı
 * - "order.created" routing key'i gelirse "stock.reserve.queue"ya gönder
 * 
 * AKIŞ:
 * Producer → Exchange (order.events) → Binding (order.created) → Queue
 * (stock.reserve.queue) → Consumer
 */
@Configuration
public class RabbitMQConfig {

    // Exchange adı - Order modülünden gelen event'ler için
    public static final String ORDER_EVENTS_EXCHANGE = "order.events";

    // Queue adı - Product modülü bu kuyruktan okuyacak
    public static final String STOCK_RESERVE_QUEUE = "stock.reserve.queue";

    // Routing key - Event tipi ile eşleşecek
    public static final String ORDER_CREATED_ROUTING_KEY = "order.created";

    /**
     * Exchange tanımı - Mesajları yönlendirir
     * DirectExchange: Exact routing key match
     */
    @Bean
    public DirectExchange orderEventsExchange() {
        return new DirectExchange(ORDER_EVENTS_EXCHANGE);
    }

    /**
     * Queue tanımı - Mesajların bekleyeceği kuyruk
     * durable=true: RabbitMQ restart olsa bile kuyruk kalıcı
     */
    @Bean
    public Queue stockReserveQueue() {
        return new Queue(STOCK_RESERVE_QUEUE, true);
    }

    /**
     * Binding - Exchange ve Queue'yu bağlar
     * "order.created" routing key'i ile gelen mesajlar stock.reserve.queue'ya gider
     */
    @Bean
    public Binding stockReserveBinding(Queue stockReserveQueue, DirectExchange orderEventsExchange) {
        return BindingBuilder
                .bind(stockReserveQueue)
                .to(orderEventsExchange)
                .with(ORDER_CREATED_ROUTING_KEY);
    }

    /**
     * JSON Message Converter - Mesajları JSON formatında gönderir/alır
     * JacksonJsonMessageConverter Spring AMQP 4.0'da Jackson 3.x kullanır
     */
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new JacksonJsonMessageConverter();
    }

    /**
     * RabbitTemplate - Mesaj göndermek için kullanılır
     */
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter jsonMessageConverter) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter);
        return template;
    }
}
