# <img src="https://github.com/user-attachments/assets/645a2bad-8a5c-4865-9aeb-5d70c1b2116b" alt="Descrição" width="40">  My Personal FinAPI - API de Gerenciamento Financeiro

API RESTful robusta para gestão e planejamento financeiro pessoal. O sistema permite o gerenciamento de contas, categorias personalizadas e transações, aplicando regras de negócio para prevenção de saldo negativo e garantindo a integridade dos dados.

Este projeto foi desenvolvido com foco em **Arquitetura Limpa**, **Segurança** e **Qualidade de Software** (Testes Automatizados).

### ✨ Funcionalidades Principais

| Módulo                          | Descrição                                                                                                           |
| ------------------------------- | ------------------------------------------------------------------------------------------------------------------- |
| **Segurança & Auth**            | Implementação do **Spring Security** para proteção dos endpoints.                                                   |
| **Gestão de Usuários**          | CRUD completo com validação de dados (Bean Validation) e regras de unicidade de e-mail.                             |
| **Controle Financeiro**         | Registro de Receitas e Despesas com atualização atômica de saldo e prevenção de *overdraft*.                        |
| **Categorização**               | Organização dinâmica de transações por categorias personalizadas.                                                   |
| **Tratamento de Erros**         | **Global Exception Handler** implementado para respostas de erro padronizadas (RFC 7807) e amigáveis ao front-end.  |

### 🛠️ Tecnologias e Conceitos Aplicados

Este projeto foi construído utilizando as seguintes tecnologias e padrões:

| Categoria                              | Tecnologias Aplicadas                                                                                                                                                   |
| -------------------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **Core**                               | <img src="https://img.shields.io/badge/Java-ED8B00?logo=openjdk&logoColor=white"> <img src="https://img.shields.io/badge/Spring_Boot_3-6DB33F?logo=spring&logoColor=white">            |
| **Banco de Dados**                     | <img src="https://img.shields.io/badge/PostgreSQL-4169E1?logo=postgresql&logoColor=white"> <img src="https://img.shields.io/badge/Spring_Data_JPA-6DB33F?logo=spring&logoColor=white">            |
| **Infraestrutura**                     | <img src="https://img.shields.io/badge/Docker-2496ED?logo=docker&logoColor=white"> <img src="https://img.shields.io/badge/Docker_Compose-2496ED?logo=docker&logoColor=white">            |
| **Qualidade & Testes**                 | <img src="https://img.shields.io/badge/JUnit_5-25A162?logo=junit5&logoColor=white"> <img src="https://img.shields.io/badge/Mockito-788BD2?logo=mockito&logoColor=white"> |
| **Documentação**                       | <img src="https://img.shields.io/badge/Swagger_UI-85EA2D?logo=swagger&logoColor=black"> <img src="https://img.shields.io/badge/OpenAPI_3-6BA539?logo=openapiinitiative&logoColor=white"> | 
| **Segurança**                          | <img src="https://img.shields.io/badge/Spring_Security-6DB33F?logo=springsecurity&logoColor=white">                                                                     |

### 🚀 Configuração e Execução Local

#### Pré-requisitos
- **Java 21**
- **Docker**
- **Maven** (Opcional, wrapper incluso)

#### ⚙️ Instalação Passo-a-Passo

```bash
# 1. Clone o repositório
git clone https://github.com/VihSousa/invest-plan.git

# 2. Suba o banco de dados com Docker
# (Certifique-se que o Docker Desktop está rodando)
docker-compose up -d

# 3. Execute a aplicação Spring Boot com Maven
./mvnw spring-boot:run
```

### 🧪 Testes Automatizado
Cobertura atual: Services (Regras de Negócio) e Controllers (Integração Web).

Para executar a bateria de testes:
```
./mvnw clean test
```

### 📄 Documentação da API
Com a aplicação rodando, acesse a documentação interativa para testar os endpoints:

▶️ http://localhost:8080/swagger-ui.html

Lá você poderá ver todos os endpoints disponíveis, seus parâmetros, e até mesmo testá-los diretamente pelo navegador.

### 🏗️ Estrutura do Código

```bash
invest-plan/
├── src/
│   ├── main/java/br/com/VihSousa/invest_plan/
│   │   ├── config/          # Configurações (Security, Swagger)
│   │   ├── controller/      # Camada REST (Endpoints)
│   │   │   └── exception/   # Tratamento Global de Erros
│   │   ├── service/         # Regras de Negócio
│   │   │   └── exception/   # Tratamento 
│   │   ├── repository/      # Acesso a Dados (JPA)
│   │   ├── model/           # Entidades (Banco de Dados)
│   │   └── dto/             # Objetos de Transferência (Dados Seguros)
│   │
│   └── test/java/br/com/VihSousa/invest_plan/
│       ├── controller/      # Testes de Integração (MockMvc)
│       └── service/         # Testes Unitários (JUnit + Mockito)
│
├── docker-compose.yml       # Orquestração dos containers (App + DB)
├── Dockerfile               # Imagem da aplicação Java
├── pom.xml                  # Gerenciamento de dependências
└── README.md                # Documentação do projeto
```

Autor ✍️
João Vítor Costa Sousa - https://github.com/VihSousa

Projeto em desenvolvido como forma de aplicar e aprofundar os conhecimentos em Java e Spring Boot, abordando desde a concepção de uma API REST até o deploy em um ambiente de nuvem.
