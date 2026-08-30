package com.gerenciador.estoque.service;


import com.gerenciador.estoque.domain.Produto;
import com.gerenciador.estoque.exception.EntradaInvalidaException;
import com.gerenciador.estoque.exception.RegistroNaoLocalizadoException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProdutoService {

    private final Map<Long, Produto> produtos = new HashMap<>();
    private static final String MENSAGEM_DEFAULT_ID_NAO_LOCALIZADO = "Produto com ID %d não encontrado.";
    private Long nextId = 1L;

    public Produto incluir(Produto produto){
        boolean naoInformouFornecedor = Objects.isNull(produto.getFornecedor());
        boolean naoInformouCategoria = Objects.isNull(produto.getCategoria());
        
        if(naoInformouCategoria){
            throw new EntradaInvalidaException("Nao eh possivel incluir produto sem informar CATEGORIA.");
        }        
        
        if(naoInformouFornecedor){
            throw new EntradaInvalidaException("Nao eh possivel incluir produto sem informar FORNECEDOR.");
        }

        produto.setId(nextId++);
        produtos.put(produto.getId(), produto);
        return produto;
    }

    public Produto alterar(Long id, Produto produtoAtualizado) {
        if (this.idProdutoInformadoNaoExistente(id)) {
            throw new RegistroNaoLocalizadoException(MENSAGEM_DEFAULT_ID_NAO_LOCALIZADO.formatted(id));
        }

        produtoAtualizado.setId(id);
        produtos.put(id, produtoAtualizado);
        return produtoAtualizado;
    }

    public void excluir(Long id) {
        if (this.idProdutoInformadoNaoExistente(id)) {
            throw new RegistroNaoLocalizadoException(MENSAGEM_DEFAULT_ID_NAO_LOCALIZADO.formatted(id));
        }
        produtos.remove(id);
    }

    public Produto obterPorId(Long id) {
        Produto produto = produtos.get(id);
        if (produto == null) {
            throw new RegistroNaoLocalizadoException(MENSAGEM_DEFAULT_ID_NAO_LOCALIZADO.formatted(id));
        }
        return produto;
    }

    public List<Produto> listarTodos() {
        return new ArrayList<>(produtos.values());
    }

    private boolean idProdutoInformadoNaoExistente(Long id){
        return !this.produtos.containsKey(id);
    }
}