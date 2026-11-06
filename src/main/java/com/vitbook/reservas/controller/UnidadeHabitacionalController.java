package com.vitbook.reservas.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.vitbook.reservas.entity.UnidadeHabitacional;
import com.vitbook.reservas.service.UnidadeHabitacionalService;

@RestController
@RequestMapping("/api/unidades")
public class UnidadeHabitacionalController {

    private final UnidadeHabitacionalService service;

    public UnidadeHabitacionalController(
            UnidadeHabitacionalService service) {
        this.service = service;
    }

    @GetMapping
    public List<UnidadeHabitacional> listarTodas() {
        return service.listarTodas();
    }

    @GetMapping("/{id}")
    public UnidadeHabitacional buscarPorId(
            @PathVariable("id") Long id) {
        return service.buscarPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UnidadeHabitacional cadastrar(
            @RequestBody UnidadeHabitacional unidade) {
        return service.cadastrar(unidade);
    }
}