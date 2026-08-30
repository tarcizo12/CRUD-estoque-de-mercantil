package com.gerenciador.estoque.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Movimentacao {
    private Long id;
    private LocalDateTime dataHora;
    private TipoMovimentacao tipo; // ENTRADA ou SAIDA
    private Usuario usuario;
    private String observacao;
    private List<ItemMovimentacao> itens = new ArrayList<>();

    public Movimentacao() {}

    public Movimentacao(TipoMovimentacao tipo, Usuario usuario, String observacao) {
        this.dataHora = LocalDateTime.now();
        this.tipo = tipo;
        this.usuario = usuario;
        this.observacao = observacao;
    }

    public void adicionarItem(ItemMovimentacao item) {
        itens.add(item);
        item.setMovimentacao(this);
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public LocalDateTime getDataHora() { return dataHora; }
    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }
    public TipoMovimentacao getTipo() { return tipo; }
    public void setTipo(TipoMovimentacao tipo) { this.tipo = tipo; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    public String getObservacao() { return observacao; }
    public void setObservacao(String observacao) { this.observacao = observacao; }
    public List<ItemMovimentacao> getItens() { return itens; }
    public void setItens(List<ItemMovimentacao> itens) { this.itens = itens; }
}
