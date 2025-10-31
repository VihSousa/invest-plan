package br.com.VihSousa.invest_plan.service;

import br.com.VihSousa.invest_plan.model.Usuario;
import br.com.VihSousa.invest_plan.repository.UsuarioRepository;
import br.com.VihSousa.invest_plan.service.exception.EmailJaCadastradoException;
import br.com.VihSousa.invest_plan.service.exception.RecursoNaoEncontradoException;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor // Construtor com os campos final
public class UsuarioService {

    // ================= APENAS UM CRUD PADRÃO ================= //

    // Campo do repositório
    private final UsuarioRepository usuarioRepository; // O Spring injeta automaticamente

    public Usuario buscarPorId(Long id) { // Método auxiliar, para evitar repetição
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado com o ID: " + id));
    }

    @Transactional
    public Usuario criarUsuario(Usuario usuarioParaCriar) {
    

        if (usuarioRepository.existsByEmail(usuarioParaCriar.getEmail())) {
            throw new EmailJaCadastradoException("Este e-mail já está em uso.");
        }

        // Passou? Ação vai para o repositório.
        return usuarioRepository.save(usuarioParaCriar);   
    }

    // Deletar indiretamente (soft delete)
    @Transactional
    public Usuario deletarUsuario(long id) {
        Usuario usuario = buscarPorId(id);
        usuario.setDeletadoEm(LocalDateTime.now());
        usuarioRepository.save(usuario);

        return usuario;
    }

    /* (Forma direta)
    public Usuario deletarUsuario(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado!"));
        usuarioRepository.delete(usuario);
        return usuario;
    }
    */

    @Transactional
    public Usuario atualizarUsuario(Long id, Usuario dadosAtualizados) {
        Usuario usuario = buscarPorId(id);

        Optional<Usuario> usuarioComNovoEmail = usuarioRepository.findByEmail(dadosAtualizados.getEmail());
        if (usuarioComNovoEmail.isPresent() && !usuarioComNovoEmail.get().getId().equals(id)) {
             throw new EmailJaCadastradoException("Este e-mail já pertence a outro usuário.");
        }

        // Atualiza os campos permitidos
        usuario.setNome(dadosAtualizados.getNome());
        usuario.setEmail(dadosAtualizados.getEmail());

        return usuarioRepository.save(usuario);
    }

}
