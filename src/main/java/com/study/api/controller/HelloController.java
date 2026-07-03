package com.study.api.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/api/hello")
    public Map<String, String> hello() {

        Map<String, String> response = new HashMap<>();
        response.put("mensagem", "Olá! API funcionando com sucesso.");

        return response;
    }
}