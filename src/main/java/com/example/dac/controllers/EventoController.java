package com.example.dac.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.example.dac.models.EdicaoEvento;
import com.example.dac.models.Evento;
import com.example.dac.models.Usuario;
import com.example.dac.repositories.AtividadeRepository;
import com.example.dac.repositories.EdicaoEventoRepository;
import com.example.dac.repositories.EventoRepository;
import com.example.dac.repositories.UsuarioRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = { "Eventos" }, description = "Eventos e suas Edições")
@RestController
@RequestMapping("/eventos")
public class EventoController {
    private final EventoRepository eventoRepository;    
    private final EdicaoEventoRepository edicaoEventoRepository;
    private final UsuarioRepository usuarioRepository;
    private final AtividadeRepository atividadeRepository;


    public EventoController(EventoRepository eventoRepository, EdicaoEventoRepository edicaoEventoRepository,
            UsuarioRepository usuarioRepository, AtividadeRepository atividadeRepository) {
        this.eventoRepository = eventoRepository;
        this.edicaoEventoRepository = edicaoEventoRepository;
        this.usuarioRepository = usuarioRepository;
        this.atividadeRepository = atividadeRepository;
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

    @PostMapping
    @ApiOperation(value = "Cadastrar evento no banco", notes = "Cadastra evento no banco de dados")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Evento cadastrado"),
            @ApiResponse(code = 401, message = "Acesso não autorizado"),
            @ApiResponse(code = 403, message = "Proibido acesso"),
            @ApiResponse(code = 404, message = "Não encontrado")
    })
    public ResponseEntity<String> cadastrarEvento(@RequestBody Evento evento) {
        eventoRepository.save(evento);

        return ResponseEntity.ok("Evento cadastrado com sucesso!");
    }

    @GetMapping("/{eventoId}")
    @ApiOperation(value = "Buscar evento no banco", notes = "Retorna evento cadastrado no banco com o ID fornecido")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Evento encontrado", response = Evento.class),
            @ApiResponse(code = 401, message = "Acesso não autorizado"),
            @ApiResponse(code = 403, message = "Proibido acesso"),
            @ApiResponse(code = 404, message = "Não encontrado")
    })
    public ResponseEntity<Evento> buscarEvento(@PathVariable("eventoId") @ApiParam(required = true, value = "ID do evento") Long eventoId) {
        if(!eventoRepository.existsById(eventoId)){
            return ResponseEntity.ok(null);
        }
        Evento evento = eventoRepository.getById(eventoId);
        return ResponseEntity.ok(evento);
    }

    @PutMapping("/{eventoId}")
    @ApiOperation(value = "Editar evento no banco", notes = "Retorna evento editada ou mensagem de erro em caso de alguma inconsistência")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Evento editado", response = Evento.class),
            @ApiResponse(code = 401, message = "Acesso não autorizado"),
            @ApiResponse(code = 403, message = "Proibido acesso"),
            @ApiResponse(code = 404, message = "Não encontrado")
    })
    public ResponseEntity<?> editarEvento(@PathVariable("eventoId") @ApiParam(required = true, value = "ID do evento") Long eventoId, @RequestBody Evento eventoEdited) {
        if(!eventoRepository.existsById(eventoId)){
            return ResponseEntity.ok("Id de evento inexistente!");
        }
        Evento evento = eventoRepository.getById(eventoId);

        evento.setCaminho(eventoEdited.getCaminho());
        evento.setDescricao(eventoEdited.getDescricao());
        evento.setNome(eventoEdited.getNome());
        evento.setSigla(eventoEdited.getSigla());

        eventoRepository.save(evento);

        return ResponseEntity.ok(evento);
    }

    @DeleteMapping("/{eventoId}")
    @ApiOperation(value = "Deletar evento do banco", notes = "Deleta evento do banco de dados caso o ID exista")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Evento deletado"),
            @ApiResponse(code = 401, message = "Acesso não autorizado"),
            @ApiResponse(code = 403, message = "Proibido acesso"),
            @ApiResponse(code = 404, message = "Não encontrado")
    })
    public ResponseEntity<String> deletarEvento(@PathVariable("eventoId") @ApiParam(required = true, value = "ID do evento") Long eventoId) {
        if(!eventoRepository.existsById(eventoId)){
            return ResponseEntity.ok("Id de evento inexistente!");
        }
        eventoRepository.deleteById(eventoId);

        return ResponseEntity.ok("Evento deletado com sucesso!");
    }

    @PostMapping("/{eventoId}/edicoes/{usuarioId}/{atividades}")
    @ApiOperation(value = "Cadastrar edição no banco", notes = "Cadastra edição no banco de dados")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Edição do evento cadastrado"),
            @ApiResponse(code = 401, message = "Acesso não autorizado"),
            @ApiResponse(code = 403, message = "Proibido acesso"),
            @ApiResponse(code = 404, message = "Não encontrado")
    })
    public ResponseEntity<String> cadastrarEdicaoEvento(@PathVariable("eventoId") @ApiParam(required = true, value = "ID do evento") Long eventoId, @PathVariable("usuarioId") @ApiParam(required = true, value = "Organizador da edição do evento") Long usuarioId, @PathVariable("atividades") @ApiParam(required = true, value = "Atividades da edição evento") Long[] atividades, @RequestBody EdicaoEvento edicaoEvento) {
        if(!eventoRepository.existsById(eventoId)){
            return ResponseEntity.ok("Id de evento inexistente!");
        }
        if(!usuarioRepository.existsById(usuarioId)){
            return ResponseEntity.ok("Organizador inexistente!");
        }
        ArrayList<Atividade> listaAtividades = new ArrayList<Atividade>();
        for (Long atividadeId : atividades) {
            if (!atividadeRepository.existsById(atividadeId)){
                return ResponseEntity.ok("Id de atividade inexistente!");
            }
            listaAtividades.add(atividadeRepository.getById(atividadeId));
        }
        if (!datasValidas(edicaoEvento.getDataInicial(), edicaoEvento.getDataFinal())) {
            return ResponseEntity.ok("Datas inválidas!");
        }

        Evento evento = eventoRepository.getById(eventoId);
        edicaoEvento.setEvento(evento);
        Usuario usuario = usuarioRepository.getById(usuarioId);
        edicaoEvento.setUsuario(usuario);
        edicaoEvento.setAtividades(listaAtividades);
        edicaoEventoRepository.save(edicaoEvento);

        return ResponseEntity.ok("Edição do evento cadastrada com sucesso!");
    }

    @PutMapping("/{eventoId}/edicoes/{edicaoId}/{usuarioId}/{atividades}")
    @ApiOperation(value = "Editar edição no banco", notes = "Retorna edição editada ou mensagem de erro em caso de alguma inconsistência")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Edição do evento editado", response = EdicaoEvento.class),
            @ApiResponse(code = 401, message = "Acesso não autorizado"),
            @ApiResponse(code = 403, message = "Proibido acesso"),
            @ApiResponse(code = 404, message = "Não encontrado")
    })
    public ResponseEntity<?> editarEdicaoEvento(@PathVariable("eventoId") @ApiParam(required = true, value = "ID do evento") Long eventoId, @PathVariable("edicaoId") @ApiParam(required = true, value = "ID da edição do evento") Long edicaoId, @PathVariable("usuarioId") @ApiParam(required = true, value = "Organizador da edição do evento") Long usuarioId, @PathVariable("atividades") @ApiParam(required = true, value = "Atividades da edição evento") Long[] atividades, @RequestBody EdicaoEvento edicaoEvento) {
        if(!edicaoEventoRepository.existsById(edicaoId)){
            return ResponseEntity.ok("Id da edição inexistente!");
        }
        if(!eventoRepository.existsById(eventoId)){
            return ResponseEntity.ok("Id de evento inexistente!");
        }
        if(!usuarioRepository.existsById(usuarioId)){
            return ResponseEntity.ok("Organizador inexistente!");
        }
        ArrayList<Atividade> listaAtividades = new ArrayList<Atividade>();
        for (Long atividadeId : atividades) {
            if (!atividadeRepository.existsById(atividadeId)){
                return ResponseEntity.ok("Id de atividade inexistente!");
            }
            listaAtividades.add(atividadeRepository.getById(atividadeId));
        }
        if (!datasValidas(edicaoEvento.getDataInicial(), edicaoEvento.getDataFinal())) {
            return ResponseEntity.ok("Datas inválidas!");
        }
        EdicaoEvento edicao = edicaoEventoRepository.getById(edicaoId);

        edicao.setAno(edicaoEvento.getAno());
        edicao.setCidade(edicaoEvento.getCidade());
        edicao.setDataFinal(edicaoEvento.getDataFinal());
        edicao.setDataInicial(edicaoEvento.getDataInicial());
        edicao.setNumero(edicaoEvento.getNumero());
        Evento evento = eventoRepository.getById(eventoId);
        edicao.setEvento(evento);
        Usuario usuario = usuarioRepository.getById(usuarioId);
        edicao.setUsuario(usuario);
        edicao.setAtividades(listaAtividades);

        edicaoEventoRepository.save(edicao);
        return ResponseEntity.ok("Edição do evento alterada com sucesso!");
    }

    @DeleteMapping("/edicoes/{edicaoId}")
    @ApiOperation(value = "Deletar edição do banco", notes = "Deleta edição do banco de dados caso o ID exista")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Edição de evento deletado"),
            @ApiResponse(code = 401, message = "Acesso não autorizado"),
            @ApiResponse(code = 403, message = "Proibido acesso"),
            @ApiResponse(code = 404, message = "Não encontrado")
    })
    public ResponseEntity<String> deletarEdicaoEvento(@PathVariable("edicaoId") @ApiParam(required = true, value = "ID da edição do evento") Long edicaoId) {
        if(!edicaoEventoRepository.existsById(edicaoId)){
            return ResponseEntity.ok("Id de edição inexistente!");
        }
        edicaoEventoRepository.deleteById(edicaoId);

        return ResponseEntity.ok("Edição deletado com sucesso!");
    }

    @GetMapping("/edicoes/{edicaoId}")
    @ApiOperation(value = "Buscar edição de evento no banco", notes = "Retorna edição de evento cadastrado no banco com o ID fornecido")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Edição de evento encontrado", response = EdicaoEvento.class),
            @ApiResponse(code = 401, message = "Acesso não autorizado"),
            @ApiResponse(code = 403, message = "Proibido acesso"),
            @ApiResponse(code = 404, message = "Não encontrado")
    })
    public ResponseEntity<EdicaoEvento> buscarEdicaoEvento(@PathVariable("edicaoId") @ApiParam(required = true, value = "ID da edição do evento") Long edicaoId) {
        if(!edicaoEventoRepository.existsById(edicaoId)){
            return ResponseEntity.ok(null);
        }
        EdicaoEvento evento = edicaoEventoRepository.getById(edicaoId);
        return ResponseEntity.ok(evento);
    }
}
