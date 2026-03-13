package com.luis.backendpedidos.service;

import com.luis.backendpedidos.model.Pedido;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class PedidoPublisherTest {

    private RabbitTemplate rabbitTemplate;
    private PedidoPublisher pedidoPublisher;

    @BeforeEach
    void setUp() {
        rabbitTemplate = mock(RabbitTemplate.class);
        pedidoPublisher = new PedidoPublisher(rabbitTemplate);
        ReflectionTestUtils.setField(pedidoPublisher, "pedidosQueue", "pedidos.entrada.luis-trindade");
    }

    @Test
    void devePublicarPedidoNaFilaCorreta() {
        Pedido pedido = new Pedido(
                UUID.randomUUID(),
                "Mouse",
                3,
                LocalDateTime.now()
        );

        pedidoPublisher.publicar(pedido);

        verify(rabbitTemplate).convertAndSend("pedidos.entrada.luis-trindade", pedido);
    }
}