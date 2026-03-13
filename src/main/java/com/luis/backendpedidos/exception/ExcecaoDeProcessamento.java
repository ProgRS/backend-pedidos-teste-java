package com.luis.backendpedidos.exception;

public class ExcecaoDeProcessamento extends RuntimeException {

    public ExcecaoDeProcessamento(String message) {
        super(message);
    }
}