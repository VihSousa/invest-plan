# 💸 My Personal FinAPI - API de Gerenciamento Financeiro

API RESTful para controle de finanças pessoais, permitindo o gerenciamento de usuários, categorias e transações de receitas e despesas. Este projeto foi desenvolvido como uma forma de aplicar e solidificar conceitos avançados do ecossistema Spring.

### ✨ Funcionalidades Principais

| Módulo                 | Descrição                                                                                                  |
| ---------------------- | ---------------------------------------------------------------------------------------------------------- |
| **Gerenciamento de Usuários** | Cadastro e consulta de usuários, com controle de saldo individual.                                  |
| **Controle de Transações** | Registro de receitas e despesas, com atualização automática do saldo do usuário a cada operação.       |
| **Organização por Categorias** | Permite a criação e associação de categorias a cada transação para facilitar a análise de gastos.  |

### 🛠️ Tecnologias e Conceitos Aplicados

Este projeto foi construído utilizando as seguintes tecnologias e padrões:

| Categoria                  | Tecnologias e Conceitos| 
| **Linguagem & Framework**  | <img src="https://img.shields.io/badge/Java-17-007396?logo=java"> <img src="https://img.shields.io/badge/Spring_Boot-3.x-6DB33F?logo=springboot"> |
| **Persistência de Dados**  | <img src="https://img.shields.io/badge/Spring_Data_JPA-000000"> <img src="https://img.shields.io/badge/Hibernate-577399"> <img src="https://img.shields.io/badge/PostgreSQL-4169E1?logo=postgresql"> <img src="https://img.shields.io/badge/Docker-2496ED?logo=docker"> |
| **API & Documentação**     | <img src="https://img.shields.io/badge/REST-000000"> <img src="https://img.shields.io/badge/OpenAPI (Swagger)-85EA2D">                                                                                                                         |
| **Ferramentas Auxiliares** | <img src="https://img.shields.io/badge/Maven-C71A36?logo=apachemaven"> <img src="https://img.shields.io/badge/Lombok-000000">                                                                                                                  |
| **Padrões de Projeto**     | Singleton (via Spring Beans), Facade e Strategy (na camada de serviço), Injeção de Dependências.                                                                                                                                      |

### 🚀 Configuração e Execução Local

#### Pré-requisitos
- Java 17+
- Maven 3.8+
- Docker e Docker Compose (ou apenas Docker Desktop)
- Git (para clonagem)

#### ⚙️ Instalação Passo-a-Passo

```bash
# 1. Clone o repositório
git clone [https://github.com/](https://github.com/)<seu-usuario-github>/my-personal-finapi.git
cd my-personal-finapi

# 2. Suba o banco de dados com Docker
# (Certifique-se que o Docker Desktop está rodando)
docker run --name finapi-db -e POSTGRES_PASSWORD=docker -p 5432:5432 -d postgres

# 3. Execute a aplicação Spring Boot com Maven
./mvnw spring-boot:run
```

A API estará disponível em http://localhost:8080.

### 📄 Documentação da API
Após iniciar a aplicação, a documentação interativa da API (gerada pelo Swagger/OpenAPI) pode ser acessada em:

▶️ http://localhost:8080/swagger-ui.html

Lá você poderá ver todos os endpoints disponíveis, seus parâmetros, e até mesmo testá-los diretamente pelo navegador.
```bash
🏗️ Estrutura do Código
my-personal-finapi/
├── src/
│   └── main/
│       ├── java/
│       │   └── io/github/vihsousa/finapi/
│       │       ├── FinapiApplication.java      → Classe principal da aplicação
│       │       ├── controller/                 → Camada de API (Endpoints REST)
│       │       ├── model/                      → Camada de Domínio (Entidades JPA)
│       │       ├── repository/                 → Camada de Acesso a Dados (Spring Data JPA)
│       │       └── service/                    → Camada de Negócio (Regras e Lógica)
│       └── resources/
│           ├── application.properties      → Configurações da aplicação
└── pom.xml                                 → Dependências e build do projeto (Maven)
```

Autor ✍️
João Vítor Costa Sousa - https://github.com/VihSousa

Projeto desenvolvido como forma de aplicar e aprofundar os conhecimentos em Java e Spring Boot, abordando desde a concepção de uma API REST até o deploy em um ambiente de nuvem.
