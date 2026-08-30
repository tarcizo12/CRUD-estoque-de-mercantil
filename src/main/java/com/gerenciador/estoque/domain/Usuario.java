package com.gerenciador.estoque.domain;

public class Usuario {
    private Long id;
    private String nome;
    private String login;
    private String senha;
    private String perfil; // ADMIN, OPERADOR

    public Usuario() {}

    public Usuario(String nome, String login, String senha, String perfil) {
        this.nome = nome;
        this.login = login;
        this.senha = senha;
        this.perfil = perfil;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
    public String getPerfil() { return perfil; }
    public void setPerfil(String perfil) { this.perfil = perfil; }

    @Override
    public String toString() {
        return "Usuario{\n" +
                "  id=" + id + ",\n" +
                "  nome='" + nome + "',\n" +
                "  login='" + login + "',\n" +
                "  perfil='" + perfil + "'\n" +
                '}';
    }
}