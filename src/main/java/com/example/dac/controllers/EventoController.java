package com.example.dac.controllers;

import java.time.LocalDate;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dac.models.EdicaoEvento;
import com.example.dac.models.Evento;
import com.example.dac.repositories.EdicaoEventoRepository;
import com.example.dac.repositories.EventoRepository;

@RestController
@RequestMapping("/eventos")
public class EventoController {
    private final EventoRepository eventoRepository;    
    private final EdicaoEventoRepository edicaoEventoRepository;

    public EventoController(EventoRepository eventoRepository, EdicaoEventoRepository edicaoEventoRepository) {
        this.eventoRepository = eventoRepository;
        this.edicaoEventoRepository = edicaoEventoRepository;
    }

    private boolean datasValidas(String dataInicial, String dataFinal) {
        LocalDate localDateInicial = LocalDate.parse(dataInicial);
        LocalDate localDateFinal = LocalDate.parse(dataFinal);

        return !localDateFinal.isBefore(localDateInicial);
    }

    @PostMapping
    public ResponseEntity<String> cadastrarEvento(@RequestBody Evento evento) {
        eventoRepository.save(evento);

        return ResponseEntity.ok("Evento cadastrado com sucesso!");
    }

    @GetMapping("/{eventoId}")
    public ResponseEntity<Evento> buscarEvento(@PathVariable("eventoId") Long eventoId) {
        if(!eventoRepository.existsById(eventoId)){
            return ResponseEntity.ok(null);
        }
        Evento evento = eventoRepository.getById(eventoId);
        return ResponseEntity.ok(evento);
    }

    @PutMapping("/{eventoId}")
    public ResponseEntity<String> editarEvento(@PathVariable("eventoId") Long eventoId, @RequestBody Evento eventoEdited) {
        if(!eventoRepository.existsById(eventoId)){
            return ResponseEntity.ok("Id de evento inexistente!");
        }
        Evento evento = eventoRepository.getById(eventoId);

        evento.setCaminho(eventoEdited.getCaminho());
        evento.setDescricao(eventoEdited.getDescricao());
        evento.setNome(eventoEdited.getNome());
        evento.setSigla(eventoEdited.getSigla());

        eventoRepository.save(evento);

        return ResponseEntity.ok("Evento editado com sucesso!");
    }

    @DeleteMapping("/{eventoId}")
    public ResponseEntity<String> deletarEvento(@PathVariable("eventoId") Long eventoId) {
        if(!eventoRepository.existsById(eventoId)){
            return ResponseEntity.ok("Id de evento inexistente!");
        }
        eventoRepository.deleteById(eventoId);

        return ResponseEntity.ok("Evento deletado com sucesso!");
    }

    @PostMapping("/{eventoId}/edicoes")
    public ResponseEntity<String> cadastrarEdicaoEvento(@PathVariable("eventoId") Long eventoId, @RequestBody EdicaoEvento edicaoEvento) {
        if(!eventoRepository.existsById(eventoId)){
            return ResponseEntity.ok("Id de evento inexistente!");
        }
        if (!datasValidas(edicaoEvento.getDataInicial(), edicaoEvento.getDataFinal())) {
            return ResponseEntity.ok("Datas inválidas!");
        }
        Evento evento = eventoRepository.getById(eventoId);
        edicaoEvento.setEvento(evento);
        edicaoEventoRepository.save(edicaoEvento);
        return ResponseEntity.ok("Edição do evento cadastrada com sucesso!");
    }

    @PutMapping("/{edicaoId}/edicoes")
    public ResponseEntity<String> editarEdicaoEvento(@PathVariable("edicaoId") Long edicaoId, @RequestBody EdicaoEvento edicaoEvento) {
        if(!edicaoEventoRepository.existsById(edicaoId)){
            return ResponseEntity.ok("Id da edição inexistente!");
        }
        EdicaoEvento edicao = edicaoEventoRepository.getById(edicaoId);

        edicao.setAno(edicaoEvento.getAno());
        edicao.setCidade(edicaoEvento.getCidade());
        edicao.setDataFinal(edicaoEvento.getDataFinal());
        edicao.setDataInicial(edicaoEvento.getDataInicial());
        edicao.setNumero(edicaoEvento.getNumero());

        edicaoEventoRepository.save(edicao);
        return ResponseEntity.ok("Edição do evento alterada com sucesso!");
    }

    @DeleteMapping("/{edicaoId}/edicoes")
    public ResponseEntity<String> deletarEdicaoEvento(@PathVariable("edicaoId") Long edicaoId) {
        if(!edicaoEventoRepository.existsById(edicaoId)){
            return ResponseEntity.ok("Id de edição inexistente!");
        }
        edicaoEventoRepository.deleteById(edicaoId);

        return ResponseEntity.ok("Edição deletado com sucesso!");
    }

    @GetMapping("/{edicaoId}/edicoes")
    public ResponseEntity<EdicaoEvento> buscarEdicaoEvento(@PathVariable("edicaoId") Long edicaoId) {
        if(!edicaoEventoRepository.existsById(edicaoId)){
            return ResponseEntity.ok(null);
        }
        EdicaoEvento evento = edicaoEventoRepository.getById(edicaoId);
        return ResponseEntity.ok(evento);
    }
}
