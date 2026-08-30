package com.gerenciador.estoque.domain;

public class ItemMovimentacao {
    private Long id;
    private Integer quantidade;
    private Produto produto;
    private Movimentacao movimentacao;

    public ItemMovimentacao() {}

    public ItemMovimentacao(Integer quantidade, Produto produto) {
        this.quantidade = quantidade;
        this.produto = produto;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Integer getQuantidade() { return quantidade; }
    public void setQuantidade(Integer quantidade) { this.quantidade = quantidade; }
    public Produto getProduto() { return produto; }
    public void setProduto(Produto produto) { this.produto = produto; }
    public Movimentacao getMovimentacao() { return movimentacao; }
    public void setMovimentacao(Movimentacao movimentacao) { this.movimentacao = movimentacao; }

    @Override
    public String toString() {
        return "ItemMovimentacao{\n" +
                "  id=" + id + ",\n" +
                "  quantidade=" + quantidade + ",\n" +
                "  produto=" + produto.getNome() + ",\n" +
                "  movimentacao=" + movimentacao + "\n" +
                '}';
    }
}