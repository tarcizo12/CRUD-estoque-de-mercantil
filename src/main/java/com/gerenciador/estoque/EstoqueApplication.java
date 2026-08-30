package com.gerenciador.estoque;

import com.gerenciador.estoque.domain.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;

@SpringBootApplication
public class EstoqueApplication {

	public static void main(String[] args) {
		//SpringApplication.run(EstoqueApplication.class, args);
        ROTINA_INSTANCIAR_OBJETOS();
	}


    public static void ROTINA_INSTANCIAR_OBJETOS(){
        Long valorDoIdParaObjetosExemplo = 1L;

        Categoria categoria = new Categoria(
                "Bebidas",
                "Produtos líquidos para consumo"
        );
        categoria.setId(valorDoIdParaObjetosExemplo);
        String toStringObjetoCategoria = categoria.toString();
        System.out.println(getLogObjeto() + toStringObjetoCategoria);

        Fornecedor fornecedor = new Fornecedor(
                "Distribuidora São Paulo",
                "12.345.678/0001-90",
                "(11) 99999-8888",
                "contato@distribuidora.com",
                "Rua das Flores, 123 - São Paulo/SP"
        );
        fornecedor.setId(valorDoIdParaObjetosExemplo);
        String toStringObjetoFornecedor = fornecedor.toString();
        System.out.println(getLogObjeto() + toStringObjetoFornecedor);

        ProdutoNaoPerecivel produtoNaoPerecivel = new ProdutoNaoPerecivel(
                "Monitor LED 24 polegadas",
                "Monitor LED Full HD de 24 polegadas",
                899.90,
                50,
                categoria,
                fornecedor,
                12
        );


        produtoNaoPerecivel.setId(valorDoIdParaObjetosExemplo);
        ItemMovimentacao item = new ItemMovimentacao(   10,produtoNaoPerecivel);
        item.setId(valorDoIdParaObjetosExemplo);
        String toStringObjetoItemMovimentacao = item.toString();
        String toStringObjetoProdutoNaoPerecivel = produtoNaoPerecivel.toString();
        System.out.println(getLogObjeto() + toStringObjetoItemMovimentacao);
        System.out.println(getLogObjeto() + toStringObjetoProdutoNaoPerecivel);

        Usuario usuario = new Usuario(
                "João da Silva",
                "joao.silva",
                "123456",
                "OPERADOR"
        );
        usuario.setId(valorDoIdParaObjetosExemplo);
        Movimentacao movimentacao = new Movimentacao(
                TipoMovimentacao.ENTRADA,
                usuario,
                "Entrada de novos monitores no estoque"
        );

        movimentacao.setId(valorDoIdParaObjetosExemplo);
        movimentacao.adicionarItem(item);
        String toStringObjetoMovimentacao = movimentacao.toString();
        String toStringObjetoUsuario = usuario.toString();
        System.out.println(getLogObjeto() + toStringObjetoMovimentacao);
        System.out.println(getLogObjeto() + toStringObjetoUsuario);

        ProdutoPerecivel produtoPerecivel = new ProdutoPerecivel(
                "Leite Integral",
                "Leite integral UHT",
                5.99,
                100,
                categoria,
                fornecedor,
                LocalDate.of(2026, 12, 30),
                "LT2026-001"
        );
        produtoPerecivel.setId(valorDoIdParaObjetosExemplo);
        String toStringObjetoPerecivel = produtoPerecivel.toString();
        System.out.println(getLogObjeto() + toStringObjetoPerecivel);
    }

    private static String getLogObjeto(){
        return "Objeto criado para visualizar o funcionamento da entidade ";
    }

}
