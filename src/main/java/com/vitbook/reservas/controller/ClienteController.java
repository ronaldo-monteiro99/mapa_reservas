package com.vitbook.reservas.controller;

import com.vitbook.reservas.dto.ClienteRequestDTO;
import com.vitbook.reservas.dto.ClienteResponseDTO;
import com.vitbook.reservas.service.ClienteService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClienteResponseDTO criar(
            @Valid @RequestBody ClienteRequestDTO cliente) {
        return clienteService.criar(cliente);
    }

    @GetMapping
    public List<ClienteResponseDTO> listarTodos() {
        return clienteService.listarTodos();
    }

    @GetMapping("/{id}")
    public ClienteResponseDTO buscarPorId(
            @PathVariable("id") Long id) {
        return clienteService.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public ClienteResponseDTO atualizar(
            @PathVariable("id") Long id,
            @Valid @RequestBody ClienteRequestDTO cliente) {
        return clienteService.atualizar(id, cliente);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable("id") Long id) {
        clienteService.excluir(id);
    }
}