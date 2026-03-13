# Backend Pedidos - Teste Prático Java

Backend desenvolvido em **Java 8** com **Spring Boot 2.7.18** para recebimento e processamento assíncrono de pedidos utilizando **RabbitMQ**.

## Tecnologias utilizadas
- Java 8
- Spring Boot 2.7.18
- Spring Web
- Spring AMQP
- Maven
- RabbitMQ
- JUnit / Mockito

## Funcionalidades implementadas
- Endpoint `POST /api/pedidos` para recebimento de pedidos
- Validação de payload
- Publicação do pedido em fila RabbitMQ
- Consumo assíncrono com `@RabbitListener`
- Simulação de processamento com sucesso ou falha
- Publicação de status em filas de sucesso e falha
- Configuração de **DLQ**
- Manutenção de status em memória
- Endpoint `GET /api/pedidos/status/{id}`
- Teste unitário do publisher

## Estrutura principal
- `controller` - endpoints REST
- `service` - regras de negócio
- `consumer` - consumo assíncrono
- `config` - configuração do RabbitMQ
- `model` - modelos de domínio
- `dto` - objetos de entrada e saída
- `exception` - tratamento de exceções

## Filas utilizadas
- `pedidos.entrada.luis-trindade`
- `pedidos.entrada.luis-trindade.dlq`
- `pedidos.status.sucesso.luis-trindade`
- `pedidos.status.falha.luis-trindade`

## Como executar
### 1. Clonar o projeto
```bash
git clone https://github.com/ProgRS/backend-pedidos-teste-java.git
