package com.vitbook.reservas.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vitbook.reservas.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
        
        Optional<Usuario> findByEmailIgnoreCase(String email);
    
}
