package com.vitbook.reservas.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import com.vitbook.reservas.dto.ReservaRequestDTO;
import com.vitbook.reservas.dto.ReservaResponseDTO;
import com.vitbook.reservas.dto.ReservaAlteracaoDatasDTO;
import com.vitbook.reservas.dto.ReservaAlteracaoStatusDTO;

import com.vitbook.reservas.service.ReservaService;

@RestController
@RequestMapping("/api/reservas")
public class ReservaController {

    private final ReservaService service;

    public ReservaController(ReservaService service) {
        this.service = service;
    }

    // Listar todas as reservas
    @GetMapping
    public List<ReservaResponseDTO> listarTodas() {
        return service.listarTodas();
    }

    // Buscar reserva por ID
    @GetMapping("/{id}")
    public ReservaResponseDTO buscarPorId(
            @PathVariable("id") Long id) {
        return service.buscarPorId(id);
    }

    // Criar uma nova reserva
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReservaResponseDTO criar(
            @Valid @RequestBody ReservaRequestDTO dados) {
        return service.criar(dados);
    }

    // Alterar datas de uma reserva
    @PatchMapping("/{id}/datas")
    public ReservaResponseDTO alterarDatas(
            @PathVariable("id") Long id,
            @Valid @RequestBody ReservaAlteracaoDatasDTO dados) {

        return service.alterarDatas(
                id,
                dados.dataEntrada(),
                dados.dataSaida()
        );
    }

    // Alterar status de uma reserva
    @PatchMapping("/{id}/status")
    public ReservaResponseDTO alterarStatus(
            @PathVariable("id") Long id,
            @Valid @RequestBody ReservaAlteracaoStatusDTO dados) {

        return service.alterarStatus(
                id,
                dados.status()
        );
    }
}