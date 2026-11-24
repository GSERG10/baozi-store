# Baozi Store — API REST

Projeto da atividade prática de **Desenvolvimento Web Back-End (UNINTER)**. A API permite cadastrar, listar, consultar e excluir clientes, produtos e pedidos de uma loja fictícia de pãozinho chinês.

## Tecnologias

Java 21 · Spring Boot 4.1.3 · Spring Data JPA · MySQL · Maven · Postman.

## Organização do projeto

- `model`: entidades JPA (`Cliente`, `Produto`, `Pedido`).
- `repository`: interfaces de acesso ao banco com Spring Data JPA.
- `controller`: endpoints HTTP da API REST.
- `application.properties`: configuração da conexão com o MySQL.

## Entidades

| Entidade | Atributos |
| --- | --- |
| Cliente | `id: Long`, `nome: String`, `clienteDesde: LocalDate` |
| Produto | `id: Long`, `nome: String`, `preco: BigDecimal`, `estoque: Boolean` |
| Pedido | `id: Long`, `clienteId: Long`, `produtoId: Long`, `quantidade: Integer` |

O campo `estoque` informa a disponibilidade (`true` ou `false`). Cada pedido contém um produto e sua quantidade; os IDs de cliente e produto são armazenados como números.

## Endpoints

**Endereço-base:** `http://localhost:8080`

| Método | Rota | Operação |
| --- | --- | --- |
| POST | `/clientes` | Cadastrar cliente |
| GET | `/clientes` | Listar clientes |
| GET | `/clientes/{id}` | Consultar cliente |
| DELETE | `/clientes/{id}` | Excluir cliente |
| POST | `/produtos` | Cadastrar produto |
| GET | `/produtos` | Listar produtos |
| GET | `/produtos/{id}` | Consultar produto |
| DELETE | `/produtos/{id}` | Excluir produto |
| POST | `/pedidos` | Cadastrar pedido |
| GET | `/pedidos` | Listar pedidos |
| GET | `/pedidos/{id}` | Consultar pedido |
| DELETE | `/pedidos/{id}` | Excluir pedido |

A atualização com `PUT` não foi implementada (opcional na atividade).

## Exemplos de cadastro (JSON)

**POST `/clientes`**

```json
{"nome":"Cliente Exemplo","clienteDesde":"2026-11-22"}
```

**POST `/produtos`**

```json
{"nome":"Baozi Tradicional","preco":14.90,"estoque":true}
```

**POST `/pedidos`**

```json
{"clienteId":1,"produtoId":1,"quantidade":3}
```

No pedido, substitua os IDs do exemplo pelos IDs retornados nos cadastros de cliente e produto.

## Como executar no Windows

**Pré-requisitos:** JDK 21 e MySQL Server em execução na porta `3306`.

1. Baixe o projeto ou clone o repositório:

   ```powershell
   git clone https://github.com/GSERG10/baozi-store.git
   cd baozi-store
   ```

2. No MySQL, com uma conta administradora, crie o banco e o usuário (escolha sua própria senha):

   ```sql
   CREATE DATABASE IF NOT EXISTS baozi_store;
   CREATE USER IF NOT EXISTS 'baozi_app'@'localhost' IDENTIFIED BY 'ESCOLHA_UMA_SENHA_FORTE';
   GRANT ALL PRIVILEGES ON baozi_store.* TO 'baozi_app'@'localhost';
   ```

   Se o usuário já existir, utilize a senha que ele já possui.

3. No PowerShell, **na pasta do projeto**, informe a senha do MySQL sem salvá-la no código:

   ```powershell
   $senhaSegura = Read-Host "Senha do usuario baozi_app" -AsSecureString
   $env:BAOZI_DB_PASSWORD = [System.Net.NetworkCredential]::new("", $senhaSegura).Password
   Remove-Variable senhaSegura
   ```

4. No **mesmo terminal**, inicie a API:

   ```powershell
   .\mvnw.cmd spring-boot:run
   ```

5. No Postman, teste os endpoints usando `Body → raw → JSON` para os cadastros. A aplicação responde, conforme a operação, com `200 OK`, `201 Created`, `204 No Content`, `400 Bad Request` ou `404 Not Found`.

O Spring Data JPA utiliza `ddl-auto=update` para criar/atualizar as tabelas após a conexão. A variável `BAOZI_DB_PASSWORD` precisa ser configurada novamente quando abrir outra sessão de terminal.

## Observações

A criação de pedidos verifica se cliente e produto existem e se a quantidade é maior que zero. A aplicação não define chaves estrangeiras JPA, não possui interface gráfica nem atualização via PUT. As evidências dos testes com Postman estão no relatório PDF da atividade.
