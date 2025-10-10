package br.com.VihSousa.invest_plan.service;

import br.com.VihSousa.invest_plan.model.Usuario;
import br.com.VihSousa.invest_plan.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@RequiredArgsConstructor // Construtor com os campos final
public class UsuarioServices {

    // ================= APENAS UM CRUD PADRÃO ================= //

    // Campo do repositório
    private final UsuarioRepository usuarioRepository; // O Spring injeta automaticamente

    private Usuario findUsuarioByIdOrThrow(Long id) { // Método auxiliar, para evitar repetição
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com o ID: " + id));
    }

    public Usuario criarUsuario(Usuario usuarioParaCriar) {
    

        if (usuarioRepository.existsByEmail(usuarioParaCriar.getEmail())) {
            throw new IllegalArgumentException("Este e-mail já está em uso.");
        }

        // Passou? Ação vai para o repositório.
        return usuarioRepository.save(usuarioParaCriar);   
    }

    // Deletar indiretamente (soft delete)
    public Usuario deletarUsuario(long id) {
        Usuario usuario = findUsuarioByIdOrThrow(id);
        usuario.setDeletadoEm(LocalDateTime.now());
        usuarioRepository.save(usuario);

        return usuario;
    }

    /* (Forma direta)
    public Usuario deletarUsuario(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));
        usuarioRepository.delete(usuario);
        return usuario;
    }
    */

    public Usuario atualizarUsuario(Long id, Usuario dadosAtualizados) {
        Usuario usuario = findUsuarioByIdOrThrow(id);

        // Atualiza os campos permitidos
        usuario.setNome(dadosAtualizados.getNome());
        usuario.setEmail(dadosAtualizados.getEmail());

        return usuarioRepository.save(usuario);
    }


    public Usuario encontrarUsuarioPorId(Long id) {
        return findUsuarioByIdOrThrow(id);
    }

}
