package com.gerenciador.estoque.service;


import com.gerenciador.estoque.domain.entity.ItemMovimentacao;
import com.gerenciador.estoque.domain.entity.Movimentacao;
import com.gerenciador.estoque.domain.entity.Produto;
import com.gerenciador.estoque.domain.enums.TipoMovimentacao;
import com.gerenciador.estoque.exception.EntradaInvalidaException;
import com.gerenciador.estoque.exception.EstoqueInsuficienteException;
import com.gerenciador.estoque.exception.RegistroNaoLocalizadoException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MovimentacaoService {

    private final ProdutoService produtoService;
    private final Map<Long, Movimentacao> movimentacoes = new HashMap<>();
    private Long nextId = 1L;

    public MovimentacaoService(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    public Movimentacao registrarMovimentacao(Movimentacao movimentacao) {
        if (movimentacao == null || movimentacao.getItens() == null || movimentacao.getItens().isEmpty()) {
            throw new EntradaInvalidaException("Movimentação deve conter pelo menos um item.");
        }
        if (movimentacao.getUsuario() == null) {
            throw new EntradaInvalidaException("Usuário responsável não informado.");
        }


        for (ItemMovimentacao item : movimentacao.getItens()) {
            Produto produto = item.getProduto();
            boolean produtoNaoExisteNoEstoque = produto == null || produto.getId() == null;

            if (produtoNaoExisteNoEstoque) {
                throw new EntradaInvalidaException("Produto do item não identificado.");
            }

            Produto produtoEstoque = produtoService.obterPorId(produto.getId());

            if (!produtoEstoque.isValido()) {
                throw new EntradaInvalidaException("Produto " + produtoEstoque.getNome() + " está vencido e não pode ser movimentado.");
            }

            Integer novaQuantidade = defineNovaQuantidadeEstoqueAposMovimentacao(movimentacao, item, produtoEstoque);

            produtoEstoque.setQuantidadeEstoque(novaQuantidade);
            produtoService.alterar(produtoEstoque.getId(), produtoEstoque);

            item.setProduto(produtoEstoque);
        }

        movimentacao.setId(nextId++);
        movimentacoes.put(movimentacao.getId(), movimentacao);

        return movimentacao;
    }

    private static Integer defineNovaQuantidadeEstoqueAposMovimentacao(Movimentacao movimentacao, ItemMovimentacao item, Produto produtoEstoque) {
        Integer novaQuantidade = produtoEstoque.getQuantidadeEstoque();

        if (movimentacao.getTipo() == TipoMovimentacao.ENTRADA) {
            novaQuantidade += item.getQuantidade();
        }else{
            if (novaQuantidade < item.getQuantidade()) {
                throw new EstoqueInsuficienteException(
                        "Estoque insuficiente para o produto " + produtoEstoque.getNome() +
                                ". Disponível: " + novaQuantidade + ", solicitado: " + item.getQuantidade()
                );
            }
            novaQuantidade -= item.getQuantidade();
        }
        return novaQuantidade;
    }

    public List<Movimentacao> listarTodas() {
        return new ArrayList<>(movimentacoes.values());
    }

    public Movimentacao obterPorId(Long id) {
        Movimentacao mov = movimentacoes.get(id);
        if (mov == null) {
            throw new RegistroNaoLocalizadoException("Movimentação com ID " + id + " não encontrada.");
        }
        return mov;
    }
}
