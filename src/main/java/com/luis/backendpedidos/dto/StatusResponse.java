package com.luis.backendpedidos.dto;

import com.luis.backendpedidos.model.StatusEnum;

import java.time.LocalDateTime;
import java.util.UUID;

public class StatusResponse {

    private UUID idPedido;
    private StatusEnum status;
    private LocalDateTime dataProcessamento;
    private String mensagemErro;

    public StatusResponse() {
    }

    public StatusResponse(UUID idPedido, StatusEnum status, LocalDateTime dataProcessamento, String mensagemErro) {
        this.idPedido = idPedido;
        this.status = status;
        this.dataProcessamento = dataProcessamento;
        this.mensagemErro = mensagemErro;
    }

    public UUID getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(UUID idPedido) {
        this.idPedido = idPedido;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public LocalDateTime getDataProcessamento() {
        return dataProcessamento;
    }

    public void setDataProcessamento(LocalDateTime dataProcessamento) {
        this.dataProcessamento = dataProcessamento;
    }

    public String getMensagemErro() {
        return mensagemErro;
    }

    public void setMensagemErro(String mensagemErro) {
        this.mensagemErro = mensagemErro;
    }
}