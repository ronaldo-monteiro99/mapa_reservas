package com.vitbook.reservas.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ReservaRequestDTO(

    @NotNull(message = "O cliente é obrigatório")
    Long clienteId,

    @NotNull(message = "A unidade habitacional é obrigatória")
    Long unidadeHabitacionalId,

    @NotNull(message = "A data de entrada é obrigatória")
    LocalDate dataEntrada,

    @NotNull(message = "A data de saída é obrigatória")
    LocalDate dataSaida,

    @NotNull(message = "A quantidade de hóspedes é obrigatória")
    @Min(value = 1, message = "Deve haver pelo menos um hóspede")
    Integer quantidadeHospedes

) {}