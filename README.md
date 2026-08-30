# CRUD - Estoque de Mercantil

Repositório destinado à entrega do trabalho final na disciplina **"Desenvolvimento de aplicações Java com Spring Boot [26E3_2]"**.

---

## Sobre o Projeto

Este projeto consiste no desenvolvimento de uma **API REST** para o gerenciamento de estoque de um mercantil (comércio varejista de alimentos e produtos diversos). A aplicação permite o controle completo de produtos, categorias, fornecedores, usuários e movimentações (entradas e saídas), com um modelo de dados que reflete as operações típicas de um estabelecimento real.

O desenvolvimento segue uma abordagem evolutiva, dividida em quatro etapas, conforme exigido pela disciplina:

1. **Modelagem orientada a objetos** – definição das classes, relacionamentos, herança e encapsulamento.
2. **Estruturas de dados e serviços** – implementação da lógica de negócio com armazenamento em memória (`Map`).
3. **API REST com Spring Boot** – exposição dos endpoints para manipulação dos recursos.
4. **Persistência com Spring Data JPA** – substituição do armazenamento em memória por banco de dados relacional.


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