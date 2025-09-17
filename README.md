# Sistema de Gestão Acadêmica

Este projeto simula um sistema de gestão acadêmica para cursos, permitindo o cadastro e acompanhamento de Cursos, Disciplinas, Alunos, Professores e Avaliações. O sistema oferece funcionalidades como o registro de dados acadêmicos, geração de boletins de alunos, controle de matrículas e avaliações de disciplinas.

## Funcionalidades

- **Cadastro de Curso**: Permite criar um curso e adicionar disciplinas, professores e alunos.
- **Cadastro de Professor**: Professores podem ser cadastrados e atribuídos a disciplinas.
- **Cadastro de Aluno**: Alunos podem se matricular em cursos e realizar avaliações nas disciplinas.
- **Avaliações**: Alunos recebem avaliações nas disciplinas, com notas e frequências registradas.
- **Boletim**: O sistema gera o boletim do aluno com suas notas e frequências.
- **Matrícula**: O sistema permite confirmar ou cancelar a matrícula de um aluno em um curso.

## Estrutura do Projeto

O projeto é composto por 9 classes principais:

### Pessoa (Classe abstrata)
Classe base para Aluno e Professor, contendo informações comuns como id, nome, cpf, e email.

### Aluno
Representa um aluno, com informações como matrícula e o curso no qual está matriculado. Permite consultar o boletim do aluno com suas avaliações.

### Professor
Representa um professor, com especialidade e disciplinas atribuídas.

### Disciplina
Representa uma disciplina de um curso, com informações sobre carga horária e avaliações associadas.

### Curso
Representa um curso, contendo informações sobre o nome, disciplinas, alunos e professores associados.

### Avaliacao
Representa a avaliação de um aluno em uma disciplina, com nota e frequência.

### Matricula
Representa a matrícula de um aluno em um curso, com a possibilidade de confirmar ou cancelar a matrícula.

### CursoService
Serviço responsável por realizar operações como criação de cursos, cadastro de professores, alunos e disciplinas, e registro de avaliações.

### Main
Classe de execução principal que integra as funcionalidades do sistema, realizando o fluxo de cadastro de curso, professor, aluno e avaliação.

## Diagrama das Entidades

O sistema é composto pelas seguintes entidades principais:

- **Curso** → contém Disciplinas, Alunos, Professores
- **Aluno** → possui Avaliações em Disciplinas
- **Professor** → leciona Disciplinas
- **Disciplina** → contém Avaliações e é lecionada por um Professor

## Requisitos

- Java 8 ou superior.

## Como rodar o projeto

Clone este repositório:

```bash
git clone https://github.com/fabricio-vianna/SchoolMaster.git
```

Compile o projeto utilizando o Maven ou Gradle (dependendo de sua preferência) ou diretamente via javac:

```bash
javac application/Main.java
```

Execute o programa:

```bash
java application.Main
```

Siga as instruções no console para cadastrar cursos, professores, alunos e realizar avaliações.

## Exemplo de Execução

Durante a execução do programa, o sistema irá solicitar as seguintes entradas:

- Nome do curso
- Dados do professor (nome, CPF, e-mail, especialidade)
- Dados da disciplina (nome, carga horária)
- Dados do aluno (nome, CPF, matrícula)
- Avaliação do aluno (nota e frequência)

Após isso, o sistema exibirá um boletim com as avaliações e frequências registradas para o aluno, além de listar todos os alunos e disciplinas do curso.

### Exemplo de saída:
```
Digite o nome do curso 1: Engenharia de Software

Digite os dados do Professor 1:
Nome: Prof. João Silva
CPF: 12345678900
Email: joao.silva@university.com
Especialidade: Programação

Digite os dados da Disciplina 1:
Nome da disciplina: Algoritmos
Carga horária (em horas): 60

Digite os dados do Aluno 1:
Nome: Maria Oliveira
CPF: 98765432100
Email: maria.oliveira@student.com
Matrícula: 2023456

Digite a avaliação do aluno:
Nota (de 0 a 10): 8.5
Frequência (% de 0 a 100): 90

Curso: Engenharia de Software
--------------------------------------------------
Professor: Prof. João Silva
CPF: 12345678900
Email: joao.silva@university.com
Especialidade: Programação
--------------------------------------------------
Disciplina: Algoritmos
Carga Horária: 60 horas
--------------------------------------------------
Aluno: Maria Oliveira
CPF: 98765432100
Email: maria.oliveira@student.com
Matrícula: 2023456
--------------------------------------------------
Boletim de Maria Oliveira
Matrícula: 2023456
--------------------------------------------------
Disciplina: Algoritmos
Carga Horária: 60 horas
Nota: 8.5
Frequência: 90%
--------------------------------------------------
Alunos matriculados no curso Engenharia de Software:
ID: 1 | Nome: Maria Oliveira | Matrícula: 2023456
--------------------------------------------------
Disciplinas do curso Engenharia de Software:
Disciplina: Algoritmos | Carga Horária: 60 horas
--------------------------------------------------
```

## Contribuições

Se você quiser contribuir para o projeto, fique à vontade! Abra uma issue ou um pull request para sugerir melhorias ou corrigir problemas.
