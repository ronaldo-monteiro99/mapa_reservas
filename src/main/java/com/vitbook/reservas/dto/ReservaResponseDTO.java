package com.vitbook.reservas.dto;

import java.time.LocalDate;

import com.vitbook.reservas.entity.StatusReserva;

public record ReservaResponseDTO(
    Long id,
    Long clienteId,
    String nomeCliente,
    Long unidadeHabitacionalId,
    Integer numeroUnidade,
    LocalDate dataEntrada,
    LocalDate dataSaida,
    StatusReserva status,
    Integer quantidadeHospedes
) {}