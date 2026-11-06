package com.vitbook.reservas.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuarios")

public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, unique = true, length = 254)
    private String email;

    @Column(nullable = false, length = 100)
    private String senha;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PerfilUsuario perfil;
    
}
