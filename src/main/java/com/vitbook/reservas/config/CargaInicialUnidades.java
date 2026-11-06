
package com.vitbook.reservas.config;

import com.vitbook.reservas.entity.UnidadeHabitacional;
import com.vitbook.reservas.repository.UnidadeHabitacionalRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CargaInicialUnidades implements CommandLineRunner {

    private final UnidadeHabitacionalRepository repository;

    public CargaInicialUnidades(
            UnidadeHabitacionalRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) {

        for (int numero = 101; numero <= 120; numero++) {

            if (!repository.existsByNumero(numero)) {

                UnidadeHabitacional unidade =
                        new UnidadeHabitacional();

                unidade.setNumero(numero);

                repository.save(unidade);
            }
        }
    }
}
