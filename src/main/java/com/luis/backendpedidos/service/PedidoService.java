package com.luis.backendpedidos.service;

import com.luis.backendpedidos.model.Pedido;
import com.luis.backendpedidos.model.StatusEnum;
import com.luis.backendpedidos.model.StatusPedido;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PedidoService {

    private final PedidoPublisher pedidoPublisher;
    private final StatusMemoriaService statusMemoriaService;

    public PedidoService(PedidoPublisher pedidoPublisher, StatusMemoriaService statusMemoriaService) {
        this.pedidoPublisher = pedidoPublisher;
        this.statusMemoriaService = statusMemoriaService;
    }

    public UUID receberPedido(Pedido pedido) {
        StatusPedido statusInicial = new StatusPedido(
                pedido.getId(),
                StatusEnum.RECEBIDO,
                null,
                null
        );

        statusMemoriaService.salvar(statusInicial);
        pedidoPublisher.publicar(pedido);

        return pedido.getId();
    }

    public StatusPedido buscarStatus(UUID idPedido) {
        return statusMemoriaService.buscarPorId(idPedido);
    }
}