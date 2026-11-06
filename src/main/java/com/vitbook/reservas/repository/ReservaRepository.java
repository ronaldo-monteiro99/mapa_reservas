
package com.vitbook.reservas.repository;

import com.vitbook.reservas.entity.Reserva;
import com.vitbook.reservas.entity.StatusReserva;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Collection;

public interface ReservaRepository
        extends JpaRepository<Reserva, Long> {

    boolean existsByUnidadeHabitacional_IdAndStatusInAndDataEntradaBeforeAndDataSaidaAfter(
            Long unidadeId,
            Collection<StatusReserva> statusBloqueantes,
            LocalDate novaDataSaida,
            LocalDate novaDataEntrada
    );

    boolean existsByUnidadeHabitacional_IdAndStatusInAndDataEntradaBeforeAndDataSaidaAfterAndIdNot(
            Long unidadeId,
            Collection<StatusReserva> statusBloqueantes,
            LocalDate novaDataSaida,
            LocalDate novaDataEntrada,
            Long reservaId
    );
    boolean existsByCliente_Id(Long clienteId);
}