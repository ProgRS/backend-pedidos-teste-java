package com.luis.backendpedidos.controller;

import com.luis.backendpedidos.dto.PedidoRequest;
import com.luis.backendpedidos.dto.PedidoResponse;
import com.luis.backendpedidos.dto.StatusResponse;
import com.luis.backendpedidos.model.Pedido;
import com.luis.backendpedidos.model.StatusPedido;
import com.luis.backendpedidos.service.PedidoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping
    public ResponseEntity<PedidoResponse> criarPedido(@Valid @RequestBody PedidoRequest request) {
        Pedido pedido = new Pedido(
                request.getId(),
                request.getProduto(),
                request.getQuantidade(),
                request.getDataCriacao()
        );

        UUID id = pedidoService.receberPedido(pedido);

        PedidoResponse response = new PedidoResponse(
                id,
                "Pedido recebido para processamento assíncrono"
        );

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }

    @GetMapping("/status/{id}")
    public ResponseEntity<StatusResponse> buscarStatus(@PathVariable("id") UUID id) {
        StatusPedido statusPedido = pedidoService.buscarStatus(id);

        if (statusPedido == null) {
            return ResponseEntity.notFound().build();
        }

        StatusResponse response = new StatusResponse(
                statusPedido.getIdPedido(),
                statusPedido.getStatus(),
                statusPedido.getDataProcessamento(),
                statusPedido.getMensagemErro()
        );

        return ResponseEntity.ok(response);
    }
}