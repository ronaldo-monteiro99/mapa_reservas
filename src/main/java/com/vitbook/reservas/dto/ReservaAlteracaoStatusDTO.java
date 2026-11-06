package com.vitbook.reservas.dto;

import com.vitbook.reservas.entity.StatusReserva;

import jakarta.validation.constraints.NotNull;

public record ReservaAlteracaoStatusDTO(

    @NotNull(message = "O status é obrigatório")
    StatusReserva status

) {}