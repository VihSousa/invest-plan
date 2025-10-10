package br.com.VihSousa.invest_plan.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.VihSousa.invest_plan.model.Categoria;
import br.com.VihSousa.invest_plan.service.CategoriaService;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    // 1. O Controller precisa do Service para executar a ação.
    // Ele NUNCA deve falar diretamente com o Repository.
    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    // 2. Este método é o "endpoint". Ele "escuta" requisições POST na URL "/categorias".
    @PostMapping
    public Categoria criarNovaCategoria(@RequestBody Categoria categoria) {
        
        // 3. O Controller DELEGA a lógica de criação para o Service
        // e retorna o resultado que o serviço lhe deu.
        return categoriaService.criarCategoria(categoria);
    }

}
