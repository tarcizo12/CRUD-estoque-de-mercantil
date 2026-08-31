# CRUD - Estoque de Produtos

Repositório destinado à entrega do trabalho final na disciplina **"Desenvolvimento de aplicações Java com Spring Boot [26E3_2]"**.

---

## Sobre o Projeto

Este projeto consiste no desenvolvimento de uma **API REST** para o gerenciamento de estoque de um mercantil (comércio varejista de alimentos e produtos diversos). A aplicação permite o controle completo de produtos, categorias, fornecedores, usuários e movimentações (entradas e saídas), com um modelo de dados que reflete as operações típicas de um estabelecimento real.

O desenvolvimento segue uma abordagem evolutiva, dividida em quatro etapas, conforme exigido pela disciplina:

1. **Modelagem orientada a objetos** – definição das classes, relacionamentos, herança e encapsulamento.
2. **Estruturas de dados e serviços** – implementação da lógica de negócio com armazenamento em memória (`Map`).
3. **API REST com Spring Boot** – exposição dos endpoints para manipulação dos recursos.
4. **Persistência com Spring Data JPA** – substituição do armazenamento em memória por banco de dados relacional.

---

## Domínio da Aplicação

### Entidades principais

| Entidade | Descrição |
|----------|-----------|
| **Categoria** | Classifica os produtos (ex.: Bebidas, Limpeza, Padaria). |
| **Fornecedor** | Empresa que abastece o mercantil com produtos. |
| **Produto** (abstrata) | Representa um item genérico, com nome, preço, quantidade em estoque, categoria e fornecedor. |
| **ProdutoPerecivel** | Subclasse de Produto, com data de validade e lote. Possui validação específica (produto vencido não é válido). |
| **ProdutoNaoPerecivel** | Subclasse de Produto, com garantia em meses. Sempre válido. |
| **Usuario** | Operador do sistema, com perfil (ADMIN, OPERADOR) para controle de acesso. |
| **Movimentacao** | Registro de uma entrada ou saída de produtos, contendo data/hora, tipo, usuário responsável e observação. |
| **ItemMovimentacao** | Detalhe de uma movimentação, associando um produto e a quantidade movimentada. |

### Diagrama de Entidades

O diagrama abaixo ilustra o modelo de classes, com todos os relacionamentos e a hierarquia de herança:

![Diagrama de Entidades - Modelo de Estoque](./diagrama-entidades.png)

### Relacionamentos (1:N)

- Uma `Categoria` pode ter vários `Produtos`.
- Um `Fornecedor` pode fornecer vários `Produtos`.
- Um `Usuario` pode registrar várias `Movimentacoes`.
- Uma `Movimentacao` pode conter vários `ItensMovimentacao`.
- Um `Produto` pode aparecer em vários `ItensMovimentacao`.

---

## Tecnologias Utilizadas

- **Java 21**
- **Spring Boot 3.3.4**
- **Spring Web** (construção da API REST)
- **SpringDoc OpenAPI (Swagger UI)** – documentação interativa
- **Maven** – gerenciamento de dependências
- **Armazenamento em memória** (`Map`) – até a Etapa 4

---

## Endpoints da API REST

A API está disponível em `http://localhost:8080` e expõe os seguintes recursos:

### Produtos (`/api/produtos`)

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET    | `/api/produtos` | Lista todos os produtos (ordenados por nome) |
| GET    | `/api/produtos/{id}` | Busca um produto pelo ID |
| POST   | `/api/produtos` | Cadastra um novo produto (perecível ou não) |
| PUT    | `/api/produtos/{id}` | Atualiza completamente um produto existente |
| DELETE | `/api/produtos/{id}` | Remove um produto do estoque |

### Movimentações (`/api/movimentacoes`)

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST   | `/api/movimentacoes` | Registra uma entrada ou saída de produtos (atualiza o estoque) |
| GET    | `/api/movimentacoes` | Lista todas as movimentações registradas |
| GET    | `/api/movimentacoes/{id}` | Busca uma movimentação pelo ID |

---

## Exemplos de Requisição

### POST `/api/produtos` – Produto não perecível

```json
{
  "nome": "Monitor LED",
  "descricao": "Monitor Full HD 24 polegadas",
  "preco": 899.90,
  "quantidadeEstoque": 50,
  "categoria": {
    "id": 1,
    "nome": "Eletrônicos",
    "descricao": "Produtos eletrônicos"
  },
  "fornecedor": {
    "id": 1,
    "nome": "Distribuidora São Paulo",
    "cnpj": "12.345.678/0001-90",
    "telefone": "(11) 99999-8888",
    "email": "contato@distribuidora.com",
    "endereco": "Rua das Flores, 123"
  },
  "perecivel": false,
  "garantiaMeses": 12
}
```

### POST `/api/movimentacoes` – Entrada de estoque

```json
{
  "tipo": "ENTRADA",
  "usuario": {
    "id": 1,
    "nome": "João Silva",
    "login": "joao.silva",
    "perfil": "OPERADOR"
  },
  "observacao": "Compra inicial",
  "itens": [
    {
      "produto": {
        "id": 1
      },
      "quantidade": 10
    }
  ]
}
```

---

## Documentação Interativa (Swagger UI)

A documentação completa da API está disponível através do **Swagger UI**, que permite visualizar todos os endpoints, testar requisições e ver os schemas dos objetos.

**Acesse:** [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

![](./CRUD.png)

---

## Como Executar o Projeto (Etapa 3 – em memória)

1. Clone o repositório:
   ```bash
   git clone https://github.com/seu-usuario/CRUD-estoque-de-mercantil.git
   ```
2. Importe o projeto como Maven em sua IDE.
3. Execute a classe `EstoqueApplication.java` (Spring Boot).
4. A aplicação iniciará na porta `8080`.
5. Use o **Swagger UI** ou ferramentas como **Postman** para testar os endpoints.

> **Atenção:** Os dados são armazenados apenas em memória (`Map`). Ao reiniciar a aplicação, todo o estado é perdido. Na Etapa 4, será implementada a persistência com JPA.

---

## Como Executar as Tags do Git (Etapas anteriores)

Para visualizar ou testar o estado do projeto em cada etapa, utilize as tags criadas no repositório:

- **Etapa 1 – Modelagem Orientada a Objetos**
  ```bash
  git checkout etapa-1
  ```  
Neste ponto, o projeto contém apenas as classes de domínio (entidades), com relacionamentos e herança, sem serviços ou API REST. Para ver exemplos de instanciação, utilize o método RUNNER_CASOS_TESTES dentro da classe EstoqueApplication. Para isso, comente a linha SpringApplication.run(EstoqueApplication.class, args); e descomente a chamada RUNNER_CASOS_TESTES(args); no método main. A execução exibirá no console objetos criados para demonstrar o funcionamento das entidades.
- **Etapa 2 – Estruturas de Dados e Serviços**
  ```bash
  git checkout etapa-2
  ```  
Aqui já estão implementados os serviços com armazenamento em memória (Map) e as regras de negócio (CRUD de produtos, movimentações, validações). Ainda não há exposição REST. Para testar a lógica, utilize o mesmo procedimento: no main, comente a linha que inicia a aplicação web e descomente RUNNER_CASOS_TESTES(args);. Esse runner executará a rotina ROTINA_PERSISTENCIA_UTILIZANDO_MAP, que popula o estoque, lista produtos, testa entradas e saídas com validações de estoque insuficiente, e exibe os resultados no console


---
