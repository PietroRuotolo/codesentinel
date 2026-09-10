# CodeSentinel — Back-End
 
**API REST em Spring Boot para monitoramento e análise de logs de erros, com persistência em PostgreSQL e filtragem dinâmica.**
 
Demo ao vivo: https://codesentinel-frontend.vercel.app
Repositório do front-end: https://github.com/PietroRuotolo/codesentinel-frontend
 
---
 
CodeSentinel é uma aplicação fullstack de monitoramento e análise de logs de erros. Este repositório contém o back-end: uma API REST em Spring Boot que recebe logs, os analisa, persiste em PostgreSQL e oferece consulta com filtragem dinâmica. O front-end (React) que consome esta API está em repositório separado (link acima).
 
A aplicação está publicada em produção — back-end containerizado com Docker no Render, banco PostgreSQL gerenciado no Neon, front-end no Vercel.
 
> Observação: o back-end roda em tier gratuito, que hiberna após períodos de inatividade. A primeira requisição após a hibernação pode levar cerca de um minuto enquanto o servidor reinicializa; as requisições seguintes são rápidas.
 
---
 
## O que a API faz
 
Recebe logs (em texto), analisa e agrupa as exceções por tipo, persiste os registros e permite consultá-los com filtros combináveis. Os principais recursos:
 
- **Análise de logs** — recebe log em texto, extrai as entradas, filtra os erros e agrupa as exceções por tipo, retornando as estatísticas em JSON.
- **Persistência com deduplicação** — os logs são gravados em PostgreSQL, com deduplicação por identidade de evento (timestamp), de modo que reprocessar o mesmo log não gera registros duplicados.
- **Filtragem dinâmica** — consulta de logs por nível, por trecho de mensagem (sem diferenciar maiúsculas) e por intervalo de datas, com os critérios aplicáveis em conjunto numa única consulta.
- **Ordenação** — os logs são retornados do mais recente para o mais antigo.
- **Tratamento de erro** — entradas inválidas recebem respostas JSON estruturadas com status HTTP apropriado.
---
 
## Endpoints principais
 
| Método | Rota | Descrição |
|--------|------|-----------|
| POST | /logs | Recebe logs em texto, analisa e persiste; retorna as estatísticas de exceções. |
| GET  | /logs | Lista os logs persistidos. Aceita filtros opcionais e combináveis por query params. |
 
Filtros aceitos no GET /logs (todos opcionais e combináveis):
 
| Parâmetro | Efeito |
|-----------|--------|
| level   | Filtra por nível (ERROR, WARN, INFO, DEBUG). |
| message | Filtra por logs cuja mensagem contém o texto (case-insensitive). |
| after   | Logs a partir da data informada (formato yyyy-MM-dd). |
| before  | Logs até a data informada (formato yyyy-MM-dd). |
 
Exemplos:
 
```
GET /logs
GET /logs?level=ERROR
GET /logs?level=ERROR&message=NullPointer
GET /logs?after=2026-08-26&before=2026-08-27
```
 
---
 
## Decisões de design
 
O que dá caráter ao projeto são as escolhas de arquitetura:
 
- **Arquitetura em camadas** — controllers finos (apenas HTTP), serviços com responsabilidade única (análise separada de consulta), e repositórios. Cada peça tem um motivo para mudar.
- **Filtragem com JPA Specifications** — em vez de multiplicar métodos de consulta ou montar SQL condicional frágil, os filtros são Specifications componíveis (Criteria API), cada uma opcional; combiná-las produz a consulta final dinamicamente.
- **Deduplicação por evento** — a identidade de um log é o par timestamp + mensagem, com o timestamp vindo do próprio evento (não do momento de processamento), o que torna a deduplicação estável mesmo ao reprocessar.
- **Configuração externalizada** — credenciais e URLs vêm de variáveis de ambiente, nunca do código, permitindo o mesmo artefato rodar em desenvolvimento e produção.
---
 
## Tecnologias
 
- Java 17, Spring Boot, Spring Data JPA / Hibernate
- PostgreSQL (local em desenvolvimento; Neon gerenciado em produção)
- Maven para build
- Docker (build multi-stage) para containerização
- Render (deploy do back-end), Neon (banco), integrado ao front-end no Vercel
---
 
## Como executar localmente
 
Pré-requisitos: JDK 17, Maven, e um PostgreSQL acessível.
 
As credenciais e a URL do banco são lidas de variáveis de ambiente. Defina:
 
```
DB_URL=jdbc:postgresql://localhost:5432/codesentinel
DB_USER=seu_usuario
PG_PASSWORD=sua_senha
```
 
Então execute:
 
```
./mvnw spring-boot:run
```
 
A aplicação sobe em http://localhost:8080 (ou na porta definida pela variável PORT).
 
---
 
## Deploy
 
O back-end é containerizado com um Dockerfile multi-stage (um estágio builda o .jar com Maven/JDK 17; outro roda com um runtime Java leve). Em produção, roda no Render, com as variáveis de ambiente apontando para o banco gerenciado no Neon. A porta é definida pela plataforma via variável PORT, respeitada pela aplicação.
 
---
 
## Direção do projeto
 
O CodeSentinel evolui em etapas. As próximas previstas: integração de IA via Spring AI para diagnóstico automático de exceptions, testes automatizados com JUnit 5 e Testcontainers, e migração do deploy para a AWS. Este documento descreve o que o projeto é hoje e cresce junto com o código.
 
---
 
**Pietro Ruotolo** — estudante de Engenharia de Software (FIAP)
 
GitHub: https://github.com/PietroRuotolo
LinkedIn: https://linkedin.com/in/pietro-ruotolo
