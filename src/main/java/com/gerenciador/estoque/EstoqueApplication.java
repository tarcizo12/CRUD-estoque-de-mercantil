package com.gerenciador.estoque;

import com.gerenciador.estoque.domain.*;
import com.gerenciador.estoque.service.ProdutoService;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.time.LocalDate;

@SpringBootApplication
public class EstoqueApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context =
                new SpringApplicationBuilder(EstoqueApplication.class)
                        .web(WebApplicationType.NONE)
                        .run(args);

        ProdutoService produtoService = context.getBean(ProdutoService.class);


        ROTINA_INSTANCIAR_OBJETOS();
        ROTINA_PERSISTENCIA_UTILIZANDO_MAP(produtoService);

        context.close();
    }


    public static void ROTINA_PERSISTENCIA_UTILIZANDO_MAP(ProdutoService produtoService) {
    }


    public static void ROTINA_INSTANCIAR_OBJETOS() {
        Long valorDoIdParaObjetosExemplo = 1L;

        Categoria categoria = new Categoria( "Bebidas", "Produtos líquidos para consumo");

        categoria.setId(valorDoIdParaObjetosExemplo);

        System.out.println( getLogObjeto() + categoria);

        Fornecedor fornecedor = new Fornecedor(
                "Distribuidora São Paulo",
                "12.345.678/0001-90",
                "(11) 99999-8888",
                "contato@distribuidora.com",
                "Rua das Flores, 123 - São Paulo/SP"
        );

        fornecedor.setId(valorDoIdParaObjetosExemplo);

        System.out.println(
                getLogObjeto() + fornecedor
        );

        ProdutoNaoPerecivel produtoNaoPerecivel =
                new ProdutoNaoPerecivel(
                        "Monitor LED 24 polegadas",
                        "Monitor LED Full HD de 24 polegadas",
                        899.90,
                        50,
                        categoria,
                        fornecedor,
                        12
        );

        produtoNaoPerecivel.setId(
                valorDoIdParaObjetosExemplo
        );

        System.out.println( getLogObjeto() + produtoNaoPerecivel);

        ItemMovimentacao item = new ItemMovimentacao( 10, produtoNaoPerecivel);

        item.setId(valorDoIdParaObjetosExemplo);

        System.out.println( getLogObjeto() + item);

        Usuario usuario = new Usuario(
                "João da Silva",
                "joao.silva",
                "123456",
                "OPERADOR"
        );

        usuario.setId(valorDoIdParaObjetosExemplo);

        System.out.println(
                getLogObjeto() + usuario
        );

        Movimentacao movimentacao =
                new Movimentacao(
                        TipoMovimentacao.ENTRADA,
                        usuario,
                        "Entrada de novos monitores no estoque"
                );

        movimentacao.setId( valorDoIdParaObjetosExemplo);

        movimentacao.adicionarItem(item);

        System.out.println( getLogObjeto() + movimentacao);

        ProdutoPerecivel produtoPerecivel =
                new ProdutoPerecivel(
                        "Leite Integral",
                        "Leite integral UHT",
                        5.99,
                        100,
                        categoria,
                        fornecedor,
                        LocalDate.of(2026, 12, 30),
                        "LT2026-001"
                );

        produtoPerecivel.setId( valorDoIdParaObjetosExemplo);

        System.out.println( getLogObjeto() + produtoPerecivel);
    }


    private static String getLogObjeto() {
        return "\nObjeto criado para visualizar " +
                "o funcionamento da entidade:\n";
    }
}
