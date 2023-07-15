package com.example.dac.controllers;

import java.util.ArrayList;

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
import com.example.dac.models.Usuario;
import com.example.dac.repositories.AtividadeRepository;
import com.example.dac.repositories.UsuarioRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = { "Usuarios" }, description = "Controlador para usuarios do sistema")
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    private final UsuarioRepository usuarioRepository;
    private final AtividadeRepository atividadeRepository;
    public UsuarioController(UsuarioRepository usuarioRepository, AtividadeRepository atividadeRepository) {
        this.usuarioRepository = usuarioRepository;
        this.atividadeRepository = atividadeRepository;
    }

    @GetMapping("/{usuarioId}")
    @ApiOperation(value = "Buscar usuario no banco", notes = "Retorna usuario cadastrado no banco com o ID fornecido")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Usuario encontrado", response = Usuario.class),
            @ApiResponse(code = 401, message = "Acesso não autorizado"),
            @ApiResponse(code = 403, message = "Proibido acesso"),
            @ApiResponse(code = 404, message = "Não encontrado")
    })
    public ResponseEntity<Usuario> buscarUsuario(@PathVariable("usuarioId") @ApiParam(required = true, value = "ID do usuario") Long usuarioId) {
        if(!usuarioRepository.existsById(usuarioId)){
            return ResponseEntity.ok(null);
        }
        Usuario usuario = usuarioRepository.getById(usuarioId);
        return ResponseEntity.ok(usuario);
    }

    @PostMapping("/{atividades}")
    @ApiOperation(value = "Cadastrar usuario no banco", notes = "Retorna usuario cadastrado ou mensagem de erro em caso de alguma inconsistência")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Usuario cadastrado", response = Usuario.class),
            @ApiResponse(code = 401, message = "Acesso não autorizado"),
            @ApiResponse(code = 403, message = "Proibido acesso"),
            @ApiResponse(code = 404, message = "Não encontrado")
    })
    public ResponseEntity<?> cadastrarUsuario(@PathVariable("atividades") @ApiParam(value = "IDs das atividades favoritas do usuario") long[] atividades, @RequestBody Usuario usuario) {
        ArrayList<Atividade> listaAtividades = new ArrayList<Atividade>();
        for (long atividadeId : atividades) {
            if (!atividadeRepository.existsById(atividadeId)){
                return ResponseEntity.ok("Id de atividade inexistente!");
            }
            listaAtividades.add(atividadeRepository.getById(atividadeId));
        }

        usuario.setAtividades(listaAtividades);
        usuarioRepository.save(usuario);

        return ResponseEntity.ok(usuario);
    }

    @PutMapping("/{usuarioId}/{atividades}")
    @ApiOperation(value = "Cadastrar usuario no banco", notes = "Retorna usuario editado ou mensagem de erro em caso de alguma inconsistência")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Usuario editado", response = Usuario.class),
            @ApiResponse(code = 401, message = "Acesso não autorizado"),
            @ApiResponse(code = 403, message = "Proibido acesso"),
            @ApiResponse(code = 404, message = "Não encontrado")
    })
    public ResponseEntity<?> editarUsuario(@PathVariable("usuarioId") @ApiParam(required = true, value = "ID do usuario") Long usuarioId, @PathVariable("atividades") @ApiParam(value = "IDs das atividades favoritas do usuario") Long[] atividades, @RequestBody Usuario usuarioEditado) {
        ArrayList<Atividade> listaAtividades = new ArrayList<Atividade>();
        for (Long atividadeId : atividades) {
            if (!atividadeRepository.existsById(atividadeId)){
                return ResponseEntity.ok("Id de atividade inexistente!");
            }
            listaAtividades.add(atividadeRepository.getById(atividadeId));
        }

        Usuario usuario = usuarioRepository.getById(usuarioId);
        usuario.setAtividades(listaAtividades);
        usuario.setAfiliacao(usuarioEditado.getAfiliacao());
        usuario.setEmail(usuarioEditado.getEmail());
        usuario.setLogin(usuarioEditado.getLogin());
        usuario.setNome(usuarioEditado.getNome());
        usuarioRepository.save(usuario);

        return ResponseEntity.ok(usuario);
    }

    @DeleteMapping("/{usuarioId}")
    @ApiOperation(value = "Deletar usuario do banco", notes = "Deleta usuario do banco de dados caso o ID exista")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Usuario deletado"),
            @ApiResponse(code = 401, message = "Acesso não autorizado"),
            @ApiResponse(code = 403, message = "Proibido acesso"),
            @ApiResponse(code = 404, message = "Não encontrado")
    })
    public ResponseEntity<String> deletarUsuario(@PathVariable("usuarioId") @ApiParam(required = true, value = "ID do usuario") Long usuarioId) {
        if(!usuarioRepository.existsById(usuarioId)){
            return ResponseEntity.ok("Id de usuario inexistente!");
        }
        usuarioRepository.deleteById(usuarioId);

        return ResponseEntity.ok("Usuario deletado com sucesso!");
    }
}
