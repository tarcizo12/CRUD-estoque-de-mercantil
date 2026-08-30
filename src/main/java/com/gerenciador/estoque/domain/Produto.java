package com.gerenciador.estoque.domain;

public abstract class Produto {
    private Long id;
    private String nome;
    private String descricao;
    private Double preco;
    private Integer quantidadeEstoque;
    private Categoria categoria;
    private Fornecedor fornecedor;

    public Produto() {}

    public Produto(String nome, String descricao, Double preco, Integer quantidadeEstoque,
                   Categoria categoria, Fornecedor fornecedor) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.quantidadeEstoque = quantidadeEstoque;
        this.categoria = categoria;
        this.fornecedor = fornecedor;
    }

    public abstract boolean isValido();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public Double getPreco() { return preco; }
    public void setPreco(Double preco) { this.preco = preco; }
    public Integer getQuantidadeEstoque() { return quantidadeEstoque; }
    public void setQuantidadeEstoque(Integer quantidadeEstoque) { this.quantidadeEstoque = quantidadeEstoque; }
    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }
    public Fornecedor getFornecedor() { return fornecedor; }
    public void setFornecedor(Fornecedor fornecedor) { this.fornecedor = fornecedor; }

    protected String dadosToString() {
        return "  id=" + id + ",\n" +
                "  nome='" + nome + "',\n" +
                "  descricao='" + descricao + "',\n" +
                "  preco=" + preco + ",\n" +
                "  quantidadeEstoque=" + quantidadeEstoque + ",\n" +
                "  categoria=" + categoria.getNome() + ",\n" +
                "  fornecedor=" + fornecedor.getNome();
    }

    @Override
    public String toString() {
        return "Produto{\n" +
                dadosToString() +
                "\n}";
    }
}