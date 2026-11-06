package com.vitbook.reservas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vitbook.reservas.entity.UnidadeHabitacional;

public interface UnidadeHabitacionalRepository
        extends JpaRepository<UnidadeHabitacional, Long> {
            boolean existsByNumero(Integer numero);
}
