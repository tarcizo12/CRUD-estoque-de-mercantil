package com.gerenciador.estoque;

import com.gerenciador.estoque.domain.entity.*;
import com.gerenciador.estoque.domain.enums.TipoMovimentacao;
import com.gerenciador.estoque.exception.EstoqueInsuficienteException;
import com.gerenciador.estoque.service.MovimentacaoService;
import com.gerenciador.estoque.service.ProdutoService;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
public class EstoqueApplication {

    public static void main(String[] args) {
        //RUNNER_CASOS_TESTES(args);
        SpringApplication.run(EstoqueApplication.class, args);
    }

    public static void RUNNER_CASOS_TESTES(String[] args) {
        ConfigurableApplicationContext context =
                new SpringApplicationBuilder(EstoqueApplication.class)
                        .web(WebApplicationType.NONE)
                        .run(args);

        ROTINA_INSTANCIAR_OBJETOS();
        ROTINA_PERSISTENCIA_UTILIZANDO_MAP(context.getBean(ProdutoService.class));

        context.close();
    }

    // Professor, neste momento foi utilizado de IA para a criação dos OBJETOS para teste
    // Informei as minhas entidades e pedi para gerar os casos de teste, para facilitar os exemplos
    public static void ROTINA_PERSISTENCIA_UTILIZANDO_MAP(ProdutoService produtoService){
        // 1. Categorias
        Categoria categoriaBebidas = new Categoria("Bebidas", "Produtos líquidos para consumo");
        Categoria categoriaEletronicos = new Categoria("Eletrônicos", "Equipamentos e dispositivos eletrônicos");
        Categoria categoriaAlimentos = new Categoria("Alimentos", "Produtos alimentícios não perecíveis");

        // 2. Fornecedores
        Fornecedor fornecedorSP = new Fornecedor(
                "Distribuidora São Paulo",
                "12.345.678/0001-90",
                "(11) 99999-8888",
                "contato@distribuidora.com",
                "Rua das Flores, 123 - São Paulo/SP"
        );

        Fornecedor fornecedorRJ = new Fornecedor(
                "Fornecedor Rio Ltda",
                "98.765.432/0001-11",
                "(21) 88888-7777",
                "vendas@fornecedorrio.com",
                "Av. Atlântica, 500 - Rio de Janeiro/RJ"
        );

        Fornecedor fornecedorMG = new Fornecedor(
                "Mercantil Mineiro",
                "45.678.901/0001-22",
                "(31) 77777-6666",
                "contato@mercantilmineiro.com",
                "Rua Minas, 45 - Belo Horizonte/MG"
        );

        // 3. Produtos não perecíveis
        ProdutoNaoPerecivel produto1 = new ProdutoNaoPerecivel(
                "Monitor LED 24 polegadas",
                "Monitor LED Full HD de 24 polegadas",
                899.90,
                50,
                categoriaEletronicos,
                fornecedorSP,
                12
        );

        ProdutoNaoPerecivel produto2 = new ProdutoNaoPerecivel(
                "Arroz Integral 5kg",
                "Arroz integral tipo 1, pacote 5kg",
                25.90,
                100,
                categoriaAlimentos,
                fornecedorMG,
                0
        );

        ProdutoNaoPerecivel produto3 = new ProdutoNaoPerecivel(
                "Fone de Ouvido Bluetooth",
                "Fone sem fio com cancelamento de ruído",
                199.90,
                30,
                categoriaEletronicos,
                fornecedorRJ,
                6
        );

        // 4. Produtos perecíveis
        ProdutoPerecivel produto4 = new ProdutoPerecivel(
                "Leite Integral 1L",
                "Leite integral pasteurizado, 1 litro",
                4.50,
                200,
                categoriaBebidas,
                fornecedorSP,
                LocalDate.now().minusDays(7),
                "L12345"
        );

        ProdutoPerecivel produto5 = new ProdutoPerecivel(
                "Queijo Mussarela 500g",
                "Queijo mussarela fatiado 500g",
                18.90,
                50,
                categoriaAlimentos,
                fornecedorMG,
                LocalDate.now().minusDays(15),
                "Q67890"
        );

        ProdutoPerecivel produto6 = new ProdutoPerecivel(
                "Suco de Laranja 1L",
                "Suco de laranja natural, 1 litro",
                6.90,
                80,
                categoriaBebidas,
                fornecedorSP,
                LocalDate.now().minusDays(10),
                "S54321"
        );

        // 5. Incluir todos no serviço (IDs gerados automaticamente)
        produtoService.incluir(produto1);
        produtoService.incluir(produto2);
        produtoService.incluir(produto3);
        produtoService.incluir(produto4);
        produtoService.incluir(produto5);
        Long ultimoRegistroInserido = produtoService.incluir(produto6).getId();


        //Metodos criados para exemplificar a etapa-2
        produtoService.listarResumoEstoqueCadastrado();

        int quantidadeProdutosValidos = produtoService.getListaProdutosValidos().size();
        int quantidadeProdutosVencidos = produtoService.getListaProdutosForaDaValidade().size();

        System.out.println("Quantidade produtos dentro da validade " + quantidadeProdutosValidos);
        System.out.println("Quantidade produtos que ja venceram " + quantidadeProdutosVencidos);
        System.out.println("Lista de produtos no estoque por ordem alfabefica ");
        System.out.println();
        for(Produto produto : produtoService.getProdutosOrdenadosPorNome()){
            System.out.println(produto.toString());
            System.out.println();
        }

        System.out.println("Ultimo produto cadastrado no estoque : " + produtoService.obterPorId(ultimoRegistroInserido).getNome());

        MovimentacaoService movService = new MovimentacaoService(produtoService);

        Usuario usuario = new Usuario("João", "joao", "123", "OPERADOR");

        //Exemplo de regra de negocio onde o usuario pode ficar impedido de mover os produtos
        // A solicitacao de saida sempre deve ser maior que a quantidade de produtos totais
        Movimentacao entrada = new Movimentacao(TipoMovimentacao.ENTRADA, usuario, "Compra inicial");
        ItemMovimentacao item1 = new ItemMovimentacao(10, produtoService.obterPorId(1L));
        ItemMovimentacao item2 = new ItemMovimentacao(5, produtoService.obterPorId(2L));
        entrada.adicionarItem(item1);
        entrada.adicionarItem(item2);

        Movimentacao movSalva = movService.registrarMovimentacao(entrada);
        System.out.println("Movimentação registrada com ID: " + movSalva.getId());


        Movimentacao saida = new Movimentacao(TipoMovimentacao.SAIDA, usuario, "Venda");
        ItemMovimentacao item3 = new ItemMovimentacao(40, produtoService.obterPorId(1L));
        saida.adicionarItem(item3);

        try {
            movService.registrarMovimentacao(saida);
        } catch (EstoqueInsuficienteException e) {
            System.out.println("Erro: " + e.getMessage());
        }
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

        System.out.println(getLogObjeto() + usuario);

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
