package com.gerenciador.estoque.service;


import com.gerenciador.estoque.domain.Produto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProdutoService {

    private final Map<Long, Produto> produtos = new HashMap<>();
    private Long nextId = 1L;

    public Produto incluir(Produto produto){
        produto.setId(nextId++);
        produtos.put(produto.getId(), produto);
        return produto;
    }

    public Produto alterar(Long id, Produto produtoAtualizado) {
        if (!produtos.containsKey(id)) {
            throw new RuntimeException("Produto com ID " + id + " não encontrado.");
        }

        produtoAtualizado.setId(id);
        produtos.put(id, produtoAtualizado);
        return produtoAtualizado;
    }

    public void excluir(Long id) {
        if (!produtos.containsKey(id)) {
            throw new RuntimeException("Produto com ID " + id + " não encontrado.");
        }
        produtos.remove(id);
    }

    public Produto obterPorId(Long id) {
        Produto produto = produtos.get(id);
        if (produto == null) {
            throw new RuntimeException("Produto com ID " + id + " não encontrado.");
        }
        return produto;
    }

    public List<Produto> listarTodos() {
        return new ArrayList<>(produtos.values());
    }

    public void limpar() {
        produtos.clear();
        nextId = 1L;
    }
}