package com.example.dac.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.dac.models.Atividade;

public interface AtividadeRepository extends JpaRepository<Atividade, Long> {
    
}
