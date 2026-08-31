package com.gerenciador.estoque.controller;

import com.gerenciador.estoque.controller.docs.ProdutoControllerDocs;
import com.gerenciador.estoque.domain.dto.ProdutoRequest;
import com.gerenciador.estoque.domain.entity.Produto;
import com.gerenciador.estoque.service.ProdutoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController implements ProdutoControllerDocs {

    private static final Logger log = LoggerFactory.getLogger(ProdutoController.class);

    @Autowired
    private ProdutoService produtoService;

    @GetMapping
    public ResponseEntity<List<Produto>> listarTodos() {
        return ResponseEntity.ok(produtoService.getProdutosOrdenadosPorNome());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> obterPorId(@PathVariable Long id) {
        return ResponseEntity.ok(produtoService.obterPorId(id));
    }

    @PostMapping
    public ResponseEntity<Produto> incluir(@RequestBody ProdutoRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoService.incluir(request.toProduto()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Produto> alterar(@PathVariable Long id, @RequestBody ProdutoRequest request) {
        return ResponseEntity.ok(produtoService.alterar(id, request.toProduto()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id){
        produtoService.excluir(id);
        log.info("Registro {} excluido com sucesso. ", id);
        return ResponseEntity.noContent().build();
    }
}