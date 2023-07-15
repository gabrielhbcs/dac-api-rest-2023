package com.example.dac.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.dac.models.Espaco;

public interface EspacoRepository extends JpaRepository<Espaco, Long> {
    
}
