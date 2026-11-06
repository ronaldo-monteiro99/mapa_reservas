package com.vitbook.reservas.repository;

import com.vitbook.reservas.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository 
    extends JpaRepository<Cliente, Long> {

}
