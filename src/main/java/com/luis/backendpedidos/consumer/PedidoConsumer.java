package com.luis.backendpedidos.consumer;

import com.luis.backendpedidos.exception.ExcecaoDeProcessamento;
import com.luis.backendpedidos.model.Pedido;
import com.luis.backendpedidos.model.StatusEnum;
import com.luis.backendpedidos.model.StatusPedido;
import com.luis.backendpedidos.service.StatusMemoriaService;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class PedidoConsumer {

    private static final Logger log = LoggerFactory.getLogger(PedidoConsumer.class);

    private final RabbitTemplate rabbitTemplate;
    private final StatusMemoriaService statusMemoriaService;

    @Value("${app.rabbit.status-sucesso-queue}")
    private String statusSucessoQueue;

    @Value("${app.rabbit.status-falha-queue}")
    private String statusFalhaQueue;

    public PedidoConsumer(RabbitTemplate rabbitTemplate, StatusMemoriaService statusMemoriaService) {
        this.rabbitTemplate = rabbitTemplate;
        this.statusMemoriaService = statusMemoriaService;
    }

    @RabbitListener(queues = "${app.rabbit.pedidos-queue}")
    public void consumir(Pedido pedido, Channel channel, Message message) throws Exception {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();

        try {
            log.info("Iniciando processamento do pedido {}", pedido.getId());

            StatusPedido processando = new StatusPedido(
                    pedido.getId(),
                    StatusEnum.PROCESSANDO,
                    null,
                    null
            );
            statusMemoriaService.salvar(processando);

            Thread.sleep(ThreadLocalRandom.current().nextLong(1000, 3001));

            double chance = ThreadLocalRandom.current().nextDouble();
            if (chance < 0.2) {
                throw new ExcecaoDeProcessamento("Falha simulada no processamento do pedido");
            }

            StatusPedido sucesso = new StatusPedido(
                    pedido.getId(),
                    StatusEnum.SUCESSO,
                    LocalDateTime.now(),
                    null
            );

            rabbitTemplate.convertAndSend(statusSucessoQueue, sucesso);
            statusMemoriaService.salvar(sucesso);

            channel.basicAck(deliveryTag, false);

            log.info("Pedido {} processado com sucesso", pedido.getId());

        } catch (ExcecaoDeProcessamento ex) {
            log.error("Falha no processamento do pedido {}", pedido.getId(), ex);

            StatusPedido falha = new StatusPedido(
                    pedido.getId(),
                    StatusEnum.FALHA,
                    LocalDateTime.now(),
                    ex.getMessage()
            );

            rabbitTemplate.convertAndSend(statusFalhaQueue, falha);
            statusMemoriaService.salvar(falha);

            channel.basicReject(deliveryTag, false);
        }
    }
}