package com.luis.backendpedidos.service;

import com.luis.backendpedidos.model.Pedido;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PedidoPublisher {

    private final RabbitTemplate rabbitTemplate;

    @Value("${app.rabbit.pedidos-queue}")
    private String pedidosQueue;

    public PedidoPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publicar(Pedido pedido) {
        rabbitTemplate.convertAndSend(pedidosQueue, pedido);
    }
}