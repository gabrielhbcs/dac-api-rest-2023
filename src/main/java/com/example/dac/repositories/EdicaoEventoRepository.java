package com.example.dac.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.dac.models.EdicaoEvento;

public interface EdicaoEventoRepository extends JpaRepository<EdicaoEvento, Long> {
    
}
