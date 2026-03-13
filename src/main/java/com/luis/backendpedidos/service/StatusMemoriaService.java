package com.luis.backendpedidos.service;

import com.luis.backendpedidos.model.StatusPedido;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class StatusMemoriaService {

    private final Map<UUID, StatusPedido> statusMap = new ConcurrentHashMap<UUID, StatusPedido>();

    public void salvar(StatusPedido statusPedido) {
        statusMap.put(statusPedido.getIdPedido(), statusPedido);
    }

    public StatusPedido buscarPorId(UUID idPedido) {
        return statusMap.get(idPedido);
    }
}