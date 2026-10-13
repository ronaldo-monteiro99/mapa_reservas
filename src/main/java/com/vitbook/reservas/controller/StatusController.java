package com.vitbook.reservas.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class StatusController {

    @GetMapping("/api/status")
    public Map<String, String> verificarStatus() {
        return Map.of(
                "sistema", "Vitbook",
                "status", "online",
                "versao", "1.0"
        );
    }
}
