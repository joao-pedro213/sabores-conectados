# Sabores Conectados API

Esta é a implementação do "Tech Challenge" da Pós-Graduação em Arquitetura e Desenvolvimento Java. A aplicação fornece uma API REST para gerenciamento de restaurantes.

## Requisitos

Para rodar esta aplicação, você precisará ter as seguintes ferramentas instaladas:

* **Java**: Versão 21.0.7
* **Docker**: Versão 28.1.1
* **Docker Compose**: Versão 2.35.1
* **Taskfile**: Versão 3.34.2 (Opcional, mas recomendado para facilitar a execução)

---

## Como Executar a Aplicação

Siga os passos abaixo para configurar e iniciar a API.

### 1. Crie o arquivo `local.env`

Este arquivo armazena as configurações essenciais da aplicação, como as credenciais de acesso ao banco de dados. Crie-o na **raiz do projeto** com o seguinte conteúdo:

```text
DB_URL=jdbc:postgresql://localhost:5432/sabores-conectados
DB_USER=appusr
DB_PASSWORD=password
```

### 2. Build e Execução dos Containers

Você pode construir e iniciar os containers da aplicação de duas maneiras:

#### 2.1. Usando Taskfile (Recomendado)

Se você tem o Taskfile instalado, basta executar o seguinte comando:

```shell
task docker-up
```

#### 2.2. Usando Docker Compose

Se preferir usar os comandos nativos do Docker Compose:

```shell
docker compose --env-file local.env build --no-cache backend
docker compose --env-file local.env up --detach
```

### 3. Acesso à Aplicação

Assim que os containers estiverem em execução, a API estará disponível na porta `8081`. Você pode acessá-la via `http://localhost:8081`.

### 4. Comandos Úteis do Taskfile

O Taskfile oferece alguns comandos convenientes para interagir com a aplicação em execução:

| Comando                   | Descrição                                         |
| :------------------------ | :------------------------------------------------ |
| `task show-backend-logs`  | Exibe os logs do container da aplicação (backend) |
| `task show-database-logs` | Exibe os logs do container do banco de dados      |
| `task docker-down`        | Interrompe e remove todos os containers da aplicação |

## Coleções do Postman

Para facilitar os testes e a interação com a API, disponibilizamos as coleções do Postman com as requisições principais.

### Como Importar:

1.  **Baixe a coleção:** Você pode baixar o arquivo JSON diretamente do repositório:
    * [`SaboresConectados.postman_collection.json`](./docs/postman/tech-challenge.json)
2.  **Importe no Postman:**
    * Abra o Postman.
    * Clique em "File" (Arquivo) -> "Import" (Importar).
    * Arraste e solte o arquivo JSON baixado ou clique em "Upload Files" (Carregar Arquivos) e selecione-o.

### Variáveis de Ambiente (Opcional, mas recomendado)

Para usar a coleção, você pode precisar configurar um ambiente no Postman com a variável `host`. Defina `host` como `http://localhost:8081`.
