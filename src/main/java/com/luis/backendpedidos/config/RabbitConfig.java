package com.luis.backendpedidos.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Value("${app.rabbit.pedidos-queue}")
    private String pedidosQueue;

    @Value("${app.rabbit.pedidos-dlq}")
    private String pedidosDlq;

    @Value("${app.rabbit.status-sucesso-queue}")
    private String statusSucessoQueue;

    @Value("${app.rabbit.status-falha-queue}")
    private String statusFalhaQueue;

    @Bean
    public Queue pedidosQueue() {
        return QueueBuilder.durable(pedidosQueue)
                .deadLetterExchange("")
                .deadLetterRoutingKey(pedidosDlq)
                .build();
    }

    @Bean
    public Queue pedidosDlq() {
        return QueueBuilder.durable(pedidosDlq).build();
    }

    @Bean
    public Queue statusSucessoQueue() {
        return QueueBuilder.durable(statusSucessoQueue).build();
    }

    @Bean
    public Queue statusFalhaQueue() {
        return QueueBuilder.durable(statusFalhaQueue).build();
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
                                         Jackson2JsonMessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }
}