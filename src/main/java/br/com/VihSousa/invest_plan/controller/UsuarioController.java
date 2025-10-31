package br.com.VihSousa.invest_plan.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.VihSousa.invest_plan.service.UsuarioService;
import br.com.VihSousa.invest_plan.model.Usuario;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/usuarios") 
@RequiredArgsConstructor
public class UsuarioController {
    
    private final UsuarioService usuarioService;

    // ============================== MÉTODOS ENDPOINTS ============================== \\

    @PostMapping
    public ResponseEntity<Usuario> criarNovaUsuario(@RequestBody Usuario usuario) {
        Usuario usuarioSalvo = usuarioService.criarUsuario(usuario);

        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioSalvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> alterarUsuario(
        @PathVariable long id,
        @RequestBody Usuario usuario
    ) {
        Usuario usuarioAtualizado = usuarioService.atualizarUsuario(id, usuario);
        return ResponseEntity.ok(usuarioAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(
        @PathVariable long id
    ) {
        usuarioService.deletarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarUsuario(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.buscarPorId(id));
    }


}
