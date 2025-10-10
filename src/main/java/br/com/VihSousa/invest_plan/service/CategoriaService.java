package br.com.VihSousa.invest_plan.service;

import org.springframework.stereotype.Service;

import br.com.VihSousa.invest_plan.model.Categoria;
import br.com.VihSousa.invest_plan.repository.CategoriaRepository;

@Service
public class CategoriaService {
    
    // O Service precisa do Repository para salvar as coisas.
    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    // Este método contém a lógica de negócio para criar uma categoria.
    public Categoria criarCategoria(Categoria categoriaParaSalvar) {
        
        // Pensar em que adicionar
        
        // O Service delega a tarefa de persistência para o Repository.
        return categoriaRepository.save(categoriaParaSalvar);
    }

}
