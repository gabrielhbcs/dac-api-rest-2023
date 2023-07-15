package com.example.dac.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dac.models.Espaco;
import com.example.dac.repositories.EspacoRepository;

import io.swagger.annotations.Api;

@Api(tags = { "Espaços" }, description = "Espaços para realização de atividades")
@RestController
@RequestMapping("/espacos")
public class EspacoController {

    private final EspacoRepository espacoRepository;

    public EspacoController(EspacoRepository espacoRepository) {
        this.espacoRepository = espacoRepository;
    }

    @PostMapping
    public ResponseEntity<String> cadastrarEspaco(@RequestBody Espaco espaco) {
        espacoRepository.save(espaco);

        return ResponseEntity.ok("Espaço cadastrado com sucesso!");
    }
    
    @GetMapping("/{espacoId}")
    public ResponseEntity<Espaco> buscarEspaco(@PathVariable("espacoId") Long espacoId) {
        if(!espacoRepository.existsById(espacoId)){
            return ResponseEntity.ok(null);
        }
        Espaco espaco = espacoRepository.getById(espacoId);
        return ResponseEntity.ok(espaco);
    }

    @PutMapping("/{espacoId}")
    public ResponseEntity<String> editarEvento(@PathVariable("espacoId") Long espacoId, @RequestBody Espaco espacoEdited) {
        if(!espacoRepository.existsById(espacoId)){
            return ResponseEntity.ok("Id de espaço inexistente!");
        }
        Espaco espaco = espacoRepository.getById(espacoId);

        espaco.setNome(espacoEdited.getNome());
        espaco.setLocalizacao(espacoEdited.getLocalizacao());
        espaco.setRecursosDisponiveis(espacoEdited.getRecursosDisponiveis());
        espaco.setCapacidade(espacoEdited.getCapacidade());

        espacoRepository.save(espaco);

        return ResponseEntity.ok("Espaço editado com sucesso!");
    }

    @DeleteMapping("/{espacoId}")
    public ResponseEntity<String> deletarEvento(@PathVariable("espacoId") Long espacoId) {
        if(!espacoRepository.existsById(espacoId)){
            return ResponseEntity.ok("Id de evento inexistente!");
        }
        espacoRepository.deleteById(espacoId);

        return ResponseEntity.ok("Espaço deletado com sucesso!");
    }
    
}
