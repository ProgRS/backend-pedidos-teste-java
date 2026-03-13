package com.luis.backendpedidos.dto;

import java.util.UUID;

public class PedidoResponse {

    private UUID id;
    private String mensagem;

    public PedidoResponse() {
    }

    public PedidoResponse(UUID id, String mensagem) {
        this.id = id;
        this.mensagem = mensagem;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}