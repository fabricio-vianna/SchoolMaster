# SchoolMaster 🎓

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-005C84?style=for-the-badge&logo=mysql&logoColor=white)
![JDBC](https://img.shields.io/badge/JDBC-DAO_Pattern-4EA94B?style=for-the-badge)
![License](https://img.shields.io/badge/License-MIT-blue?style=for-the-badge)

O **SchoolMaster** é um sistema de gestão acadêmica robusto desenvolvido em Java. O projeto simula o gerenciamento de
uma instituição de ensino, utilizando uma arquitetura profissional com **JDBC (Java Database Connectivity)** e o padrão
de projeto **DAO (Data Access Object)** para persistência de dados em banco **MySQL**.

O sistema permite o cadastro e acompanhamento completo de Cursos, Disciplinas, Alunos, Professores e Avaliações,
incluindo a geração de boletins e relatórios de matrícula.

## 🚀 Funcionalidades

* **Cadastro de Curso:** Criação de cursos e gerenciamento de grades curriculares.
* **Gestão de Pessoas:**
    * **Professores:** Cadastro com especialidades e atribuição a disciplinas.
    * **Alunos:** Matrícula em cursos e histórico acadêmico.
* **Gestão Acadêmica:**
    * **Disciplinas:** Controle de carga horária e corpo docente.
    * **Matrículas:** Sistema para ativar, cancelar ou trancar matrículas.
* **Sistema de Avaliação:** Registro de notas e frequências com cálculo automático de desempenho.
* **Relatórios:** Geração de boletim escolar detalhado via console.

## 📂 Estrutura do Projeto

O projeto segue uma arquitetura em camadas para garantir a separação de responsabilidades e facilidade de manutenção:

```text
SchoolMaster/
├── src/
│   ├── application/       # Ponto de entrada (Program.java) e interação com usuário
│   ├── db/                # Gerenciamento de conexão JDBC e Properties
│   ├── model/
│   │   ├── dao/           # Interfaces (contratos) e Factory do padrão DAO
│   │   │   └── impl/      # Implementação concreta dos DAOs (SQL/JDBC)
│   │   └── entities/      # Classes de domínio (POJOs)
│   └── services/          # Regras de negócio e orquestração de serviços
├── .gitignore             # Arquivos ignorados pelo Git
├── db.properties          # Configuração de credenciais do Banco de Dados
└── README.md              # Documentação do projeto
Entidades Principais
Pessoa (Abstrata): Classe base para Aluno e Professor.

Aluno: Vinculado a uma matrícula, possui histórico de avaliações.

Professor: Especialista responsável por lecionar disciplinas.

Curso: Entidade agregadora de disciplinas e alunos.

Disciplina: Matéria específica com carga horária definida.

Avaliacao: Registro de nota (0-10) e frequência (%) de um aluno.

Matricula: Vínculo associativo entre Aluno e Curso.

🗄️ Diagrama de Entidades
O modelo de dados relacional segue a seguinte estrutura lógica:

Snippet de código

graph TD;
    Curso -->|contém| Disciplina
    Curso -->|tem| Aluno
    Curso -->|tem| Professor
    Professor -->|leciona| Disciplina
    Aluno -->|possui| Avaliacao
    Disciplina -->|gera| Avaliacao
(Visualização simplificada: Curso 1:N Disciplinas/Alunos | Professor 1:N Disciplinas | Aluno 1:N Avaliações)

🛠️ Tecnologias & Requisitos
Java JDK 8+

MySQL Server (8.0 ou superior recomendado)

JDBC Driver (MySQL Connector/J)

Maven ou Gradle (Opcional para gerenciamento de dependências)

🔧 Instalação e Execução
1. Clone o Repositório
Bash

git clone [https://github.com/fabricio-vianna/SchoolMaster.git](https://github.com/fabricio-vianna/SchoolMaster.git)
cd SchoolMaster
2. Configuração do Banco de Dados
Crie um arquivo chamado db.properties na raiz do projeto (se não houver) com as suas credenciais locais do MySQL:

Properties

dburl=jdbc:mysql://localhost:3306/schoolmaster?useSSL=false
user=seu_usuario_mysql
password=sua_senha_mysql
Nota: Certifique-se de criar o schema schoolmaster no seu banco de dados antes de rodar.

3. Compilação e Execução
Você pode executar via IDE (IntelliJ/Eclipse) ou via terminal:

Bash

# Compilar
javac -cp .:mysql-connector.jar application/Program.java

# Executar
java -cp .:mysql-connector.jar application.Program
🖥️ Exemplo de Uso
Ao iniciar a aplicação, siga as instruções interativas no console:

Plaintext

=== SISTEMA DE GESTÃO ACADÊMICA ===
Digite o nome do curso 1: Engenharia de Software

Digite os dados do Professor 1:
Nome: Prof. João Silva
CPF: 123.456.789-00
Email: joao@university.com
Especialidade: Java Back-end

... [Entrada de dados do Aluno e Disciplina] ...

Digite a avaliação do aluno:
Nota (de 0 a 10): 9.5
Frequência (% de 0 a 100): 100

--------------------------------------------------
Boletim de Maria Oliveira
Matrícula: 2023456
--------------------------------------------------
Disciplina: Java Back-end
Carga Horária: 80 horas
Nota: 9.5
Frequência: 100%
--------------------------------------------------
🤝 Contribuições
Contribuições são bem-vindas!

Faça um Fork do projeto.

Crie uma Branch para sua feature (git checkout -b feature/NovaFeature).

Faça o Commit (git commit -m 'Adicionando nova feature').

Faça o Push (git push origin feature/NovaFeature).

Abra um Pull Request.

📄 Licença
Este projeto está sob a licença MIT. Veja o arquivo LICENSE para mais detalhes.