package com.luis.backendpedidos.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> tratarValidacao(MethodArgumentNotValidException ex) {
        List<String> detalhes = new ArrayList<String>();

        ex.getBindingResult().getFieldErrors().forEach(erro ->
                detalhes.add(erro.getField() + ": " + erro.getDefaultMessage())
        );

        Map<String, Object> resposta = new HashMap<String, Object>();
        resposta.put("erro", "Dados inválidos");
        resposta.put("detalhes", detalhes);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resposta);
    }
}