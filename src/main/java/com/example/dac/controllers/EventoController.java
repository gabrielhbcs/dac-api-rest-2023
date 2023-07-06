package com.example.dac.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dac.models.EdicaoEvento;
import com.example.dac.models.Evento;
import com.example.dac.repositories.EventoRepository;

@RestController
@RequestMapping("/eventos")
public class EventoController {
    private final EventoRepository eventoRepository;

    public EventoController(EventoRepository eventoRepository) {
        this.eventoRepository = eventoRepository;
    }

    @PostMapping
    public ResponseEntity<String> cadastrarEvento(@RequestBody Evento evento) {
        eventoRepository.save(evento);

        return ResponseEntity.ok("Evento cadastrado com sucesso!");
    }

    @PostMapping("/{eventoId}/edicoes")
    public ResponseEntity<String> cadastrarEdicaoEvento(@PathVariable("eventoId") Long eventoId, @RequestBody EdicaoEvento edicaoEvento) {
        // Aqui você pode adicionar a lógica para validar os dados da edição do evento
        // e verificar se o evento com o ID fornecido já existe, por exemplo

        // Se os dados forem válidos, você pode salvar a edição do evento no banco de dados ou fazer qualquer outra ação necessária
        // Exemplo: edicaoEventoService.cadastrarEdicaoEvento(eventoId, edicaoEvento);

        return ResponseEntity.ok("Edição do evento cadastrada com sucesso!");
    }
}
