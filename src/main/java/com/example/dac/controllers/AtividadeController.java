package com.example.dac.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
import com.example.dac.repositories.AtividadeRepository;
import com.example.dac.repositories.EspacoRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = { "Atividades" }, description = "Atividades de um possível evento")
@RestController
@RequestMapping("/atividades")
public class AtividadeController {
    private final AtividadeRepository atividadeRepository;
    private final EspacoRepository espacoRepository;


    public AtividadeController(AtividadeRepository atividadeRepository, EspacoRepository espacoRepository) {
        this.atividadeRepository = atividadeRepository;
        this.espacoRepository = espacoRepository;
    }

    private boolean datasValidas(String dataInicial, String dataFinal) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        try {
            Date localDateInicial = format.parse(dataInicial);
            Date localDateFinal = format.parse(dataFinal);
            return !localDateFinal.before(localDateInicial);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

    @GetMapping("/{atividadeId}")
    @ApiOperation(value = "Buscar atividade no banco", notes = "Retorna atividade cadastrada no banco com o ID fornecido")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Atividade encontrada", response = Atividade.class),
            @ApiResponse(code = 401, message = "Acesso não autorizado"),
            @ApiResponse(code = 403, message = "Proibido acesso"),
            @ApiResponse(code = 404, message = "Não encontrado")
    })
    public ResponseEntity<Atividade> buscarAtividade(@PathVariable("atividadeId") @ApiParam(required = true, value = "ID da atividade") Long atividadeId) {
        if(!atividadeRepository.existsById(atividadeId)){
            return ResponseEntity.ok(null);
        }
        Atividade atividade = atividadeRepository.getById(atividadeId);
        return ResponseEntity.ok(atividade);
    }

    @PostMapping("/{espacoId}")
    @ApiOperation(value = "Cadastrar atividade no banco", notes = "Retorna atividade cadastrada ou mensagem de erro em caso de alguma inconsistência")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Atividade cadastrada", response = Atividade.class),
            @ApiResponse(code = 401, message = "Acesso não autorizado"),
            @ApiResponse(code = 403, message = "Proibido acesso"),
            @ApiResponse(code = 404, message = "Não encontrado")
    })
    public ResponseEntity<?> cadastrarAtividade(@PathVariable("espacoId") @ApiParam(required = true, value = "ID do espaço da atividade") Long espacoId, @RequestBody Atividade atividade) {
        if(!espacoRepository.existsById(espacoId)){
            return ResponseEntity.ok("Id de espaço inexistente!");
        }
        if (!datasValidas(atividade.getHorarioInicial(), atividade.getHorarioFinal())) {
            return ResponseEntity.ok("Datas inválidas!");
        }

        Espaco espaco = espacoRepository.getById(espacoId);
        atividade.setEspaco(espaco);

        atividadeRepository.save(atividade);

        return ResponseEntity.ok(atividade);
    }

    @PutMapping("/{espacoId}/{atividadeId}")
    @ApiOperation(value = "Editar atividade no banco", notes = "Retorna atividade editada ou mensagem de erro em caso de alguma inconsistência")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Atividade editada", response = Atividade.class),
            @ApiResponse(code = 401, message = "Acesso não autorizado"),
            @ApiResponse(code = 403, message = "Proibido acesso"),
            @ApiResponse(code = 404, message = "Não encontrado")
    })
    public ResponseEntity<?> editarAtividade(@PathVariable("espacoId") @ApiParam(required = true, value = "ID do espaço da atividade para edição") Long espacoId, @PathVariable("atividadeId") @ApiParam(required = true, value = "ID da atividade") Long atividadeId, @RequestBody Atividade atividadeEditado) {
        if(!atividadeRepository.existsById(atividadeId)){
            return ResponseEntity.ok("Id da atividade inexistente!");
        }
        if(!espacoRepository.existsById(espacoId)){
            return ResponseEntity.ok("Id de espaço inexistente!");
        }
        if (!datasValidas(atividadeEditado.getHorarioInicial(), atividadeEditado.getHorarioFinal())) {
            return ResponseEntity.ok("Datas inválidas!");
        }
        Atividade atividade = atividadeRepository.getById(atividadeId);

        atividade.setData(atividadeEditado.getData());
        atividade.setDescricao(atividadeEditado.getDescricao());
        atividade.setEspaco(atividadeEditado.getEspaco());
        atividade.setHorarioFinal(atividadeEditado.getHorarioFinal());
        atividade.setHorarioInicial(atividadeEditado.getHorarioInicial());
        atividade.setNome(atividadeEditado.getNome());
        atividade.setTipo(atividadeEditado.getTipo());

        atividadeRepository.save(atividade);
        return ResponseEntity.ok(atividade);
    }

    @DeleteMapping("/{atividadeId}")
    @ApiOperation(value = "Deletar atividade do banco", notes = "Deleta atividade do banco de dados caso o ID exista")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Atividade deletada"),
            @ApiResponse(code = 401, message = "Acesso não autorizado"),
            @ApiResponse(code = 403, message = "Proibido acesso"),
            @ApiResponse(code = 404, message = "Não encontrado")
    })
    public ResponseEntity<String> deletarAtividade(@PathVariable("atividadeId") @ApiParam(required = true, value = "ID da atividade") Long atividadeId) {
        if(!atividadeRepository.existsById(atividadeId)){
            return ResponseEntity.ok("Id de atividade inexistente!");
        }
        atividadeRepository.deleteById(atividadeId);

        return ResponseEntity.ok("Atividade deletada com sucesso!");
    }
}
