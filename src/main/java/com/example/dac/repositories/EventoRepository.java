package com.example.dac.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.dac.models.Evento;

public interface EventoRepository extends JpaRepository<Evento, Long> {
}
