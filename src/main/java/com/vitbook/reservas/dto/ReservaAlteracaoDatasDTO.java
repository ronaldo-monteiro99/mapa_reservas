package com.vitbook.reservas.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;

public record ReservaAlteracaoDatasDTO(

    @NotNull(message = "A data de entrada é obrigatória")
    LocalDate dataEntrada,

    @NotNull(message = "A data de saída é obrigatória")
    LocalDate dataSaida

) {}