package com.example.dac.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.dac.models.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
}
