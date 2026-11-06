package com.vitbook.reservas.service;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.vitbook.reservas.entity.UnidadeHabitacional;
import com.vitbook.reservas.repository.UnidadeHabitacionalRepository;

@Service
public class UnidadeHabitacionalService {

    private final UnidadeHabitacionalRepository repository;

    public UnidadeHabitacionalService(
            UnidadeHabitacionalRepository repository) {
        this.repository = repository;
    }

    public List<UnidadeHabitacional> listarTodas() {
        return repository.findAll(Sort.by("numero"));
    }

    public UnidadeHabitacional buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Unidade habitacional não encontrada"));
    }

    public UnidadeHabitacional cadastrar(
            UnidadeHabitacional unidade) {

        if (unidade.getNumero() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "O número da unidade é obrigatório");
        }

        if (repository.existsByNumero(unidade.getNumero())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Já existe uma unidade com esse número");
        }

        return repository.save(unidade);
    }
}