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

import com.example.dac.models.Atividade;
import com.example.dac.models.Espaco;
import com.example.dac.repositories.EspacoRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = { "Espaços" }, description = "Espaços para realização de atividades")
@RestController
@RequestMapping("/espacos")
public class EspacoController {

    private final EspacoRepository espacoRepository;

    public EspacoController(EspacoRepository espacoRepository) {
        this.espacoRepository = espacoRepository;
    }

    @PostMapping
    @ApiOperation(value = "Cadastrar espaço no banco", notes = "Retorna espaço cadastrado")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Atividade cadastrada", response = Atividade.class),
            @ApiResponse(code = 401, message = "Acesso não autorizado"),
            @ApiResponse(code = 403, message = "Proibido acesso"),
            @ApiResponse(code = 404, message = "Não encontrado")
    })
    public ResponseEntity<?> cadastrarEspaco(@RequestBody Espaco espaco) {
        espacoRepository.save(espaco);

        return ResponseEntity.ok(espaco);
    }
    
    @GetMapping("/{espacoId}")
    @ApiOperation(value = "Buscar espaço no banco", notes = "Retorna espaço cadastrada no banco com o ID fornecido")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Espaço encontrada", response = Espaco.class),
            @ApiResponse(code = 401, message = "Acesso não autorizado"),
            @ApiResponse(code = 403, message = "Proibido acesso"),
            @ApiResponse(code = 404, message = "Não encontrado")
    })
    public ResponseEntity<Espaco> buscarEspaco(@PathVariable("espacoId") @ApiParam(required = true, value = "ID do espaço") Long espacoId) {
        if(!espacoRepository.existsById(espacoId)){
            return ResponseEntity.ok(null);
        }
        Espaco espaco = espacoRepository.getById(espacoId);
        return ResponseEntity.ok(espaco);
    }

    @PutMapping("/{espacoId}")
    @ApiOperation(value = "Editar espaço no banco", notes = "Retorna espaço editada ou mensagem de erro em caso de alguma inconsistência")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Espaço editado", response = Espaco.class),
            @ApiResponse(code = 401, message = "Acesso não autorizado"),
            @ApiResponse(code = 403, message = "Proibido acesso"),
            @ApiResponse(code = 404, message = "Não encontrado")
    })
    public ResponseEntity<String> editarEvento(@PathVariable("espacoId") @ApiParam(required = true, value = "ID do espaço") Long espacoId, @RequestBody Espaco espacoEdited) {
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
    @ApiOperation(value = "Deletar espaço do banco", notes = "Deleta espaço do banco de dados caso o ID exista")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Espaço deletado"),
            @ApiResponse(code = 401, message = "Acesso não autorizado"),
            @ApiResponse(code = 403, message = "Proibido acesso"),
            @ApiResponse(code = 404, message = "Não encontrado")
    })
    public ResponseEntity<String> deletarEvento(@PathVariable("espacoId") @ApiParam(required = true, value = "ID do espaço") Long espacoId) {
        if(!espacoRepository.existsById(espacoId)){
            return ResponseEntity.ok("Id de evento inexistente!");
        }
        espacoRepository.deleteById(espacoId);

        return ResponseEntity.ok("Espaço deletado com sucesso!");
    }
    
}
