package com.gerenciador.estoque.service;


import com.gerenciador.estoque.domain.entity.Produto;
import com.gerenciador.estoque.exception.EntradaInvalidaException;
import com.gerenciador.estoque.exception.RegistroNaoLocalizadoException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProdutoService {

    private final Map<Long, Produto> produtos = new HashMap<>();
    private static final String MENSAGEM_DEFAULT_ID_NAO_LOCALIZADO = "Produto com ID %d não encontrado.";
    private Long nextId = 1L;

    public void excluir(Long id) {
        if (!produtos.containsKey(id)) {
            throw new RegistroNaoLocalizadoException(MENSAGEM_DEFAULT_ID_NAO_LOCALIZADO.formatted(id));
        }
        produtos.remove(id);
    }

    public List<Produto> listarTodos() {
        return new ArrayList<>(produtos.values());
    }

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


    public Produto obterPorId(Long id) {
        Produto produto = produtos.get(id);
        if (produto == null){
            throw new RegistroNaoLocalizadoException(MENSAGEM_DEFAULT_ID_NAO_LOCALIZADO.formatted(id));
        }
        return produto;
    }

    public void listarResumoEstoqueCadastrado(){
        System.out.println("=== Produtos cadastrados ===");

        new ArrayList<>(produtos.values()).forEach(p ->
                System.out.printf("ID: %d - %s (Estoque: %d)%n",
                        p.getId(), p.getNome(), p.getQuantidadeEstoque())
        );
    }

    public List<Produto> getListaProdutosValidos() {
        return produtos.values().stream()
                .filter(Produto::isValido)
                .collect(Collectors.toList());
    }

    public List<Produto> getListaProdutosForaDaValidade() {
        return produtos.values().stream()
                .filter(p -> !p.isValido())
                .collect(Collectors.toList());
    }

    public List<Produto> getProdutosOrdenadosPorNome() {
        return this.listarTodos().stream()
                .sorted(Comparator.comparing(Produto::getNome, String.CASE_INSENSITIVE_ORDER))
                .collect(Collectors.toList());
    }

    private boolean idProdutoInformadoNaoExistente(Long id){
        return !this.produtos.containsKey(id);
    }
}