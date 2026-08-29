package com.gerenciador.estoque.controller;  // ajuste para seu pacote

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")          // prefixo opcional
public class EstoqueController {

    @GetMapping("/consultar")
    public Map<String, String> consultar() {
        Map<String, String> resposta = new HashMap<>();
        resposta.put("mensagem", "Consulta realizada com sucesso!");
        resposta.put("status", "OK");
        return resposta;
    }
}