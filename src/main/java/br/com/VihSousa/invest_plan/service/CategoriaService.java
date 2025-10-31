package br.com.VihSousa.invest_plan.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.VihSousa.invest_plan.model.Categoria;
import br.com.VihSousa.invest_plan.repository.CategoriaRepository;
import br.com.VihSousa.invest_plan.service.exception.CategoriaJaExistenteException;
import br.com.VihSousa.invest_plan.service.exception.RecursoNaoEncontradoException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoriaService {
    
    // O Service precisa do Repository para salvar as coisas.
    private final CategoriaRepository categoriaRepository;

    @Transactional
    public Categoria criarCategoria(Categoria categoriaParaSalvar) {
        
        if (categoriaRepository.existsByNome(categoriaParaSalvar.getNome())) {
            throw new CategoriaJaExistenteException("Uma categoria com este nome já existe.");
        }
        // DELEGAÇÃO: Pede para a ferramenta (repositório) salvar a nova categoria.
        return categoriaRepository.save(categoriaParaSalvar);

    }

    @Transactional
    public void deletarCategoria(Long id) {
        // Verifica se a categoria existe antes de tentar deletá-la
        if (!categoriaRepository.existsById(id)) {
            throw new RecursoNaoEncontradoException("Categoria não encontrada!");
        }
        categoriaRepository.deleteById(id);
    }

    @Transactional
    public Categoria atualizarCategoria(Long id, Categoria categoriaAtualizada) {
        Categoria categoriaExistente = this.buscarPorId(id);
        String novoNome = categoriaAtualizada.getNome();
        Optional<Categoria> categoriaComNovoNome = categoriaRepository.findByNome(novoNome);

        if (categoriaComNovoNome.isPresent() && !categoriaComNovoNome.get().getId().equals(id)) {
            // Se o nome existe E o ID é diferente do ID da categoria que estamos atualizando...
            throw new CategoriaJaExistenteException("Este nome já está sendo usado por outra categoria.");
        }

        categoriaExistente.setNome(novoNome);
        return categoriaRepository.save(categoriaExistente);
    }

    public Categoria buscarPorId(Long id) {
        // DELEGAÇÃO COM TRATAMENTO DE ERRO:
        // Pede para a ferramenta buscar pelo ID. Se não encontrar, lança uma exceção.
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Categoria não encontrada!"));
    }

    public List<Categoria> listarTodasCategorias() {
        return categoriaRepository.findAll();
    }

}
