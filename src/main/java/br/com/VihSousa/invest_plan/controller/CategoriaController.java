package br.com.VihSousa.invest_plan.controller;

import java.util.List; 
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.VihSousa.invest_plan.model.Categoria;
import br.com.VihSousa.invest_plan.service.CategoriaService;
import lombok.RequiredArgsConstructor;

@RestController //Apenas recebe as requisições HTTP
@RequestMapping("/categorias") //Endereço base
@RequiredArgsConstructor
public class CategoriaController {

    // O Controller precisa do Service para executar a ação.
    private final CategoriaService categoriaService;

    // ============================== MÉTODOS ENDPOINTS ============================== \\

    @PostMapping
    public ResponseEntity<Categoria> criarNovaCategoria(@RequestBody Categoria categoria) {
        Categoria categoriaSalva = categoriaService.criarCategoria(categoria);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaSalva);
    }

    @PutMapping("/{id}") 
    public ResponseEntity<Categoria> alterarCategoria(
            @PathVariable Long id,
            @RequestBody Categoria categoria
        ) {
        Categoria categoriaAtualizada = categoriaService.atualizarCategoria(id, categoria);
        return ResponseEntity.ok(categoriaAtualizada);
    }

    @DeleteMapping("/{id}")
    // Não precisa retornar o objeto deletado, apenas um status de sucesso
    public ResponseEntity<Void> deletarCategoria(
        @PathVariable Long id
    ) {
        categoriaService.deletarCategoria(id); 
        return ResponseEntity.noContent().build(); // Retorna 204 No Content
    }

    @GetMapping
    public ResponseEntity<List<Categoria>> listarTodasCategorias() {
        return ResponseEntity.ok(categoriaService.listarTodasCategorias());
    }

    // Busca uma única categoria
    @GetMapping("/{id}")
    public ResponseEntity<Categoria> buscarCategoriaPorId(@PathVariable Long id) {
        return ResponseEntity.ok(categoriaService.buscarPorId(id));
    }
}
