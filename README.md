<div align="center">
# 🛡️ CodeSentinel

### Monitoramento de erros com IA — diagnostica *exceptions*, explica a causa e sugere a correção automaticamente.

[![Java](https://img.shields.io/badge/Java-17-orange?logo=openjdk&logoColor=white)](https://openjdk.org/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-6DB33F?logo=springboot&logoColor=white)](https://spring.io/projects/spring-boot)
[![Spring AI](https://img.shields.io/badge/Spring%20AI-LLM%20%7C%20RAG%20%7C%20Tool%20Calling-6DB33F?logo=spring&logoColor=white)](https://spring.io/projects/spring-ai)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-4169E1?logo=postgresql&logoColor=white)](https://www.postgresql.org/)
[![Docker](https://img.shields.io/badge/Docker-Compose-2496ED?logo=docker&logoColor=white)](https://www.docker.com/)
[![CI](https://img.shields.io/badge/CI%2FCD-GitHub%20Actions-2088FF?logo=githubactions&logoColor=white)](https://github.com/features/actions)
[![Status](https://img.shields.io/badge/status-em%20desenvolvimento%20ativo-yellow)]()

</div>
---

##  O que é

**CodeSentinel** é um serviço de observabilidade que recebe *logs* e *stack traces* de outras aplicações, usa um **LLM (via Spring AI)** para diagnosticar a causa raiz de cada erro em linguagem natural e, quando configurado, **abre uma issue automaticamente** com a análise e a correção sugerida.

A ideia nasceu de uma dor real: times pequenos gastam horas lendo *stack traces* repetidos. O CodeSentinel faz a primeira leitura por você.

>  **Projeto de estudo**, construído para aprofundar **Java + Spring AI + automação**. Não é um produto de produção.

---

## Funcionalidades

-  **Ingestão de erros via API REST** — outras aplicações enviam *logs* e *stack traces* por HTTP.
-  **Diagnóstico por IA** — o LLM analisa o *stack trace* e explica a causa provável em português claro.
-  **Sugestão de correção** — resposta estruturada com o trecho provável do problema e o caminho da solução.
-  **Abertura automática de issues** — via *tool calling* do Spring AI, o modelo cria a issue no repositório-alvo.
-  **Histórico persistente** — cada erro e diagnóstico fica salvo no PostgreSQL, com *migrations* versionadas.
-  **Ambiente 100% reproduzível** — sobe tudo com um `docker compose up`.
---

##  Arquitetura

```mermaid
flowchart LR
    App["Aplicação monitorada"] -->|POST /api/errors| API["API REST<br/>Spring Boot"]
    API --> Service["Camada de serviço"]
    Service --> DB[("PostgreSQL<br/>histórico + migrations")]
    Service --> AI["Spring AI<br/>análise do stack trace"]
    AI --> LLM(["LLM"])
    LLM -->|diagnóstico + correção| AI
    AI -->|tool calling| GH["GitHub API<br/>abre issue"]
    AI --> Service
    Service -->|resposta| API
```
 
---

##  Como funciona o diagnóstico

```mermaid
sequenceDiagram
    participant App as Aplicação
    participant API as CodeSentinel API
    participant AI as Spring AI
    participant LLM as LLM
    participant GH as GitHub
 
    App->>API: envia stack trace (POST /api/errors)
    API->>API: persiste o erro (PostgreSQL)
    API->>AI: solicita análise
    AI->>LLM: prompt com o stack trace
    LLM-->>AI: causa raiz + correção sugerida
    AI->>GH: cria issue (tool calling)
    GH-->>AI: nº da issue
    AI-->>API: diagnóstico estruturado
    API-->>App: 201 + diagnóstico
```
 
---

##  Stack

| Camada | Tecnologia |
|---|---|
| Linguagem | Java 17 |
| Framework | Spring Boot 3, Spring Web |
| IA | Spring AI (LLM, *tool calling*) |
| Persistência | Spring Data JPA + PostgreSQL |
| Migrations | Flyway |
| Testes | JUnit 5 + Testcontainers |
| Infra | Docker Compose |
| CI/CD | GitHub Actions |
 
---

##  Rodando localmente

**Pré-requisitos:** Docker e Docker Compose instalados.

```bash
# 1. Clone o repositório
git clone https://github.com/PietroRuotolo/codesentinel.git
cd codesentinel
 
# 2. Configure as variáveis de ambiente
cp .env.example .env
# edite o .env com sua chave de LLM e o token do GitHub
 
# 3. Suba a aplicação + banco
docker compose up --build
```

A API sobe em `http://localhost:8080`.
 
---

##  Exemplo de uso

Enviando um erro para diagnóstico:

```bash
curl -X POST http://localhost:8080/api/errors \
  -H "Content-Type: application/json" \
  -d '{
    "service": "checkout-service",
    "level": "ERROR",
    "stackTrace": "java.lang.NullPointerException: Cannot invoke \"Order.getTotal()\" because \"order\" is null\n\tat com.loja.CheckoutService.finalize(CheckoutService.java:42)"
  }'
```

Resposta:

```json
{
  "id": 128,
  "diagnosis": "O erro ocorre porque o objeto 'order' está nulo ao chamar getTotal(). Provável causa: o pedido não foi encontrado antes da finalização.",
  "suggestedFix": "Validar se 'order' não é nulo antes da linha 42, ou lançar uma exceção de negócio quando o pedido não existir.",
  "issueUrl": "https://github.com/PietroRuotolo/loja/issues/57"
}
```

> Substitua este exemplo pelos endpoints e respostas reais do seu projeto conforme ele evolui._
 
---

##  Testes

```bash
./mvnw test
```

Os testes de integração usam **Testcontainers** para subir um PostgreSQL real em container, garantindo que a persistência seja validada contra um banco de verdade — não um mock.

---

##  Autor

**Pietro Schimidt Ruotolo** — Estudante de Engenharia de Software (FIAP)

[![GitHub](https://img.shields.io/badge/GitHub-PietroRuotolo-181717?logo=github)](https://github.com/PietroRuotolo)
[![LinkedIn](https://img.shields.io/badge/LinkedIn-pietro--ruotolo-0A66C2?logo=linkedin&logoColor=white)](https://linkedin.com/in/pietro-ruotolo)
 
---

<div align="center">
<sub>Construído como estudo aprofundado de Java, Spring AI e automação. ⭐ Se achou interessante, deixe uma estrela!</sub>
</div>