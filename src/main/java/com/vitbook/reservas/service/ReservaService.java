package com.vitbook.reservas.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.vitbook.reservas.dto.ReservaRequestDTO;
import com.vitbook.reservas.dto.ReservaResponseDTO;

import com.vitbook.reservas.entity.Cliente;
import com.vitbook.reservas.entity.Reserva;
import com.vitbook.reservas.entity.StatusReserva;
import com.vitbook.reservas.entity.UnidadeHabitacional;

import com.vitbook.reservas.repository.ClienteRepository;
import com.vitbook.reservas.repository.ReservaRepository;
import com.vitbook.reservas.repository.UnidadeHabitacionalRepository;

@Service
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final ClienteRepository clienteRepository;
    private final UnidadeHabitacionalRepository unidadeRepository;

    private static final List<StatusReserva> STATUS_BLOQUEANTES =
            List.of(
                    StatusReserva.CONFIRMADA,
                    StatusReserva.CONCLUIDA
            );

    public ReservaService(
            ReservaRepository reservaRepository,
            ClienteRepository clienteRepository,
            UnidadeHabitacionalRepository unidadeRepository) {

        this.reservaRepository = reservaRepository;
        this.clienteRepository = clienteRepository;
        this.unidadeRepository = unidadeRepository;
    }

    @Transactional(readOnly = true)
    public List<ReservaResponseDTO> listarTodas() {

        return reservaRepository.findAll()
                .stream()
                .map(this::converterParaDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public ReservaResponseDTO buscarPorId(Long id) {

        Reserva reserva = buscarEntidadePorId(id);

        return converterParaDTO(reserva);
    }

    @Transactional
    public ReservaResponseDTO criar(ReservaRequestDTO dados) {

        validarPeriodo(
                dados.dataEntrada(),
                dados.dataSaida()
        );

        if (dados.clienteId() == null
                || dados.unidadeHabitacionalId() == null) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Cliente e unidade são obrigatórios"
            );
        }

        if (dados.quantidadeHospedes() == null
                || dados.quantidadeHospedes() < 1) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "A quantidade de hóspedes deve ser maior que zero"
            );
        }

        Cliente cliente = clienteRepository
                .findById(dados.clienteId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Cliente não encontrado"
                ));

        UnidadeHabitacional unidade = unidadeRepository
                .findById(dados.unidadeHabitacionalId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Unidade habitacional não encontrada"
                ));

        boolean existeConflito = reservaRepository
                .existsByUnidadeHabitacional_IdAndStatusInAndDataEntradaBeforeAndDataSaidaAfter(
                        unidade.getId(),
                        STATUS_BLOQUEANTES,
                        dados.dataSaida(),
                        dados.dataEntrada()
                );

        if (existeConflito) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "A unidade já possui uma reserva nesse período"
            );
        }

        Reserva reserva = new Reserva();

        reserva.setCliente(cliente);
        reserva.setUnidadeHabitacional(unidade);
        reserva.setDataEntrada(dados.dataEntrada());
        reserva.setDataSaida(dados.dataSaida());
        reserva.setQuantidadeHospedes(dados.quantidadeHospedes());
        reserva.setStatus(StatusReserva.CONFIRMADA);

        Reserva reservaSalva = reservaRepository.save(reserva);

        return converterParaDTO(reservaSalva);
    }

    @Transactional
    public ReservaResponseDTO alterarDatas(
            Long reservaId,
            LocalDate novaDataEntrada,
            LocalDate novaDataSaida) {

        Reserva reserva = buscarEntidadePorId(reservaId);

        if (reserva.getStatus() != StatusReserva.CONFIRMADA) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Somente reservas confirmadas podem ter as datas alteradas"
            );
        }

        validarPeriodo(novaDataEntrada, novaDataSaida);

        boolean existeConflito = reservaRepository
                .existsByUnidadeHabitacional_IdAndStatusInAndDataEntradaBeforeAndDataSaidaAfterAndIdNot(
                        reserva.getUnidadeHabitacional().getId(),
                        STATUS_BLOQUEANTES,
                        novaDataSaida,
                        novaDataEntrada,
                        reservaId
                );

        if (existeConflito) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "A unidade já possui uma reserva nesse período"
            );
        }

        reserva.setDataEntrada(novaDataEntrada);
        reserva.setDataSaida(novaDataSaida);

        Reserva reservaSalva = reservaRepository.save(reserva);

        return converterParaDTO(reservaSalva);
    }

    @Transactional
    public ReservaResponseDTO alterarStatus(
            Long reservaId,
            StatusReserva novoStatus) {

        Reserva reserva = buscarEntidadePorId(reservaId);

        if (novoStatus == null
                || novoStatus == StatusReserva.CONFIRMADA) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Informe um status válido para a alteração"
            );
        }

        if (reserva.getStatus() != StatusReserva.CONFIRMADA) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Somente reservas confirmadas podem ter o status alterado"
            );
        }

        reserva.setStatus(novoStatus);

        Reserva reservaSalva = reservaRepository.save(reserva);

        return converterParaDTO(reservaSalva);
    }

    private Reserva buscarEntidadePorId(Long id) {

        return reservaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Reserva não encontrada"
                ));
    }

    private ReservaResponseDTO converterParaDTO(Reserva reserva) {

        return new ReservaResponseDTO(
                reserva.getId(),
                reserva.getCliente().getId(),
                reserva.getCliente().getNome(),
                reserva.getUnidadeHabitacional().getId(),
                reserva.getUnidadeHabitacional().getNumero(),
                reserva.getDataEntrada(),
                reserva.getDataSaida(),
                reserva.getStatus(),
                reserva.getQuantidadeHospedes()
        );
    }

    private void validarPeriodo(
            LocalDate dataEntrada,
            LocalDate dataSaida) {

        if (dataEntrada == null || dataSaida == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "As datas de entrada e saída são obrigatórias"
            );
        }

        if (!dataSaida.isAfter(dataEntrada)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "A data de saída deve ser posterior à data de entrada"
            );
        }
    }
}