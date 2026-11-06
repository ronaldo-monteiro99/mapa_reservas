package com.vitbook.reservas.service;

import com.vitbook.reservas.dto.ClienteRequestDTO;
import com.vitbook.reservas.dto.ClienteResponseDTO;
import com.vitbook.reservas.entity.Cliente;
import com.vitbook.reservas.repository.ClienteRepository;
import com.vitbook.reservas.repository.ReservaRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final ReservaRepository reservaRepository;

    public ClienteService(
            ClienteRepository clienteRepository,
            ReservaRepository reservaRepository) {
        this.clienteRepository = clienteRepository;
        this.reservaRepository = reservaRepository;
    }

    @Transactional(readOnly = true)
    public List<ClienteResponseDTO> listarTodos() {
        return clienteRepository.findAll()
                .stream()
                .map(this::paraResponseDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public ClienteResponseDTO buscarPorId(Long id) {
        return paraResponseDTO(buscarEntidadePorId(id));
    }

    @Transactional
    public ClienteResponseDTO criar(ClienteRequestDTO dados) {
        Cliente cliente = new Cliente();

        preencherDados(cliente, dados);

        Cliente salvo = clienteRepository.save(cliente);

        return paraResponseDTO(salvo);
    }

    @Transactional
    public ClienteResponseDTO atualizar(
            Long id,
            ClienteRequestDTO dados) {

        Cliente cliente = buscarEntidadePorId(id);

        preencherDados(cliente, dados);

        Cliente atualizado = clienteRepository.save(cliente);

        return paraResponseDTO(atualizado);
    }

    @Transactional
    public void excluir(Long id) {
        Cliente cliente = buscarEntidadePorId(id);

        if (reservaRepository.existsByCliente_Id(id)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Não é possível excluir um cliente que possui reservas."
            );
        }

        clienteRepository.delete(cliente);
    }

    private Cliente buscarEntidadePorId(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Cliente não encontrado"
                ));
    }

    private void preencherDados(
            Cliente cliente,
            ClienteRequestDTO dados) {

        cliente.setNome(dados.nome());
        cliente.setTelefone(dados.telefone());
        cliente.setEmail(dados.email());
    }

    private ClienteResponseDTO paraResponseDTO(Cliente cliente) {
        return new ClienteResponseDTO(
                cliente.getId(),
                cliente.getNome(),
                cliente.getTelefone(),
                cliente.getEmail()
        );
    }
}