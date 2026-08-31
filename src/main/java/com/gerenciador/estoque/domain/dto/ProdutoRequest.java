package com.gerenciador.estoque.domain.dto;

import com.gerenciador.estoque.domain.entity.*;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

public class ProdutoRequest {
    @Schema(description = "Nome do produto", example = "Monitor LED")
    private String nome;

    @Schema(description = "Descrição detalhada", example = "Monitor LED 24 polegadas Full HD")
    private String descricao;

    @Schema(description = "Preço unitário", example = "899.90")
    private Double preco;

    @Schema(description = "Quantidade em estoque", example = "50")
    private Integer quantidadeEstoque;

    @Schema(description = "Categoria do produto (objeto completo)")
    private CategoriaRequest categoria;

    @Schema(description = "Fornecedor do produto (objeto completo)")
    private FornecedorRequest fornecedor;

    @Schema(description = "Indica se o produto é perecível", example = "false")
    private boolean perecivel;

    @Schema(description = "Data de validade (obrigatório se perecivel)", example = "2026-12-31")
    private LocalDate dataValidade;

    @Schema(description = "Lote (obrigatório se perecivel)", example = "L12345")
    private String lote;

    @Schema(description = "Garantia em meses (obrigatório se não perecivel)", example = "12")
    private Integer garantiaMeses;

    public Produto toProduto() {
        Categoria categoria = this.getCategoria().toEntity();
        Fornecedor fornecedor = this.getFornecedor().toEntity();

        if (this.isPerecivel()) {
            return new ProdutoPerecivel(
                    this.getNome(),
                    this.getDescricao(),
                    this.getPreco(),
                    this.getQuantidadeEstoque(),
                    categoria,
                    fornecedor,
                    this.getDataValidade(),
                    this.getLote()
            );
        } else {
            return new ProdutoNaoPerecivel(
                    this.getNome(),
                    this.getDescricao(),
                    this.getPreco(),
                    this.getQuantidadeEstoque(),
                    categoria,
                    fornecedor,
                    this.getGarantiaMeses()
            );
        }
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getGarantiaMeses() {
        return garantiaMeses;
    }

    public void setGarantiaMeses(Integer garantiaMeses) {
        this.garantiaMeses = garantiaMeses;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public LocalDate getDataValidade() {
        return dataValidade;
    }

    public void setDataValidade(LocalDate dataValidade) {
        this.dataValidade = dataValidade;
    }

    public boolean isPerecivel() {
        return perecivel;
    }

    public void setPerecivel(boolean perecivel) {
        this.perecivel = perecivel;
    }

    public FornecedorRequest getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(FornecedorRequest fornecedor) {
        this.fornecedor = fornecedor;
    }

    public CategoriaRequest getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaRequest categoria) {
        this.categoria = categoria;
    }

    public Integer getQuantidadeEstoque() {
        return quantidadeEstoque;
    }

    public void setQuantidadeEstoque(Integer quantidadeEstoque) {
        this.quantidadeEstoque = quantidadeEstoque;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}