package application;

import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import model.dao.*;
import model.entities.*;
import services.CursoService;
import static services.CursoService.adicionarProfessorAoCurso;

public class Program {

    public static void main(String[] args) {

        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);

        AlunoDao alunoDao = DaoFactory.createAlunoDao();
        AvaliacaoDao avaliacaoDao = DaoFactory.createAvaliacaoDao();
        CursoDao cursoDao = DaoFactory.createCursoDao();
        DisciplinaDao disciplinaDao = DaoFactory.createDisciplinaDao();
        MatriculaDao matriculaDao = DaoFactory.createMatriculaDao();
        ProfessorDao professorDao = DaoFactory.createProfessorDao();

        // Se precisar testar o banco, descomente a linha abaixo:
        // testarBancoDeDados(alunoDao, avaliacaoDao, cursoDao, disciplinaDao, matriculaDao, professorDao);

        System.out.println("=== SISTEMA DE GESTÃO ACADÊMICA ===");

        System.out.print("Digite o nome do curso 1: ");
        String nomeCurso = sc.nextLine();

        Curso curso = CursoService.criarCurso(nomeCurso);

        System.out.println("\nDigite os dados do Professor 1:");
        System.out.print("Nome: ");
        String nomeProfessor = sc.nextLine();

        System.out.print("CPF: ");
        String cpfProfessor = sc.nextLine();

        System.out.print("Email: ");
        String emailProfessor = sc.nextLine();

        System.out.print("Especialidade: ");
        String esplProfessor = sc.nextLine();

        Professor professor = adicionarProfessorAoCurso(nomeProfessor, cpfProfessor, emailProfessor, esplProfessor, curso);

        System.out.println("\nDigite os dados da Disciplina 1: ");
        System.out.print("Nome da disciplina: ");
        String nomeDisciplina = sc.nextLine();

        System.out.print("Carga horária (em horas): ");
        int hrDisciplina = sc.nextInt();
        sc.nextLine();

        Disciplina disciplinaOne = CursoService.adicionarDisciplinaAoCurso(1, nomeDisciplina, hrDisciplina, professor, curso);

        System.out.println("\nDigite os dados do Aluno 1:");
        System.out.print("Nome: ");
        String nomeAluno = sc.nextLine();

        System.out.print("CPF: ");
        String cpfAluno = sc.nextLine();

        System.out.print("Email: ");
        String emailAluno = sc.nextLine();

        System.out.print("Matrícula: ");
        String mtlAluno = sc.nextLine();

        Aluno alunoOne = CursoService.adicionarAlunoAoCurso(nomeAluno, cpfAluno, emailAluno, mtlAluno, curso);

        System.out.println("\nDigite a avaliação do aluno:");
        double notaAluno;
        do {
            System.out.print("Nota (de 0 a 10): ");
            notaAluno = sc.nextDouble();
        } while (notaAluno < 0.0 || notaAluno > 10.0);

        int frqcAluno;
        do {
            System.out.print("Frequência (% de 0 a 100): ");
            frqcAluno = sc.nextInt();
        } while (frqcAluno < 0 || frqcAluno > 100);
        sc.nextLine();

        Avaliacao avaliacao = CursoService.registrarAvaliacao(1, alunoOne, disciplinaOne, notaAluno, frqcAluno);

        System.out.println("\nCurso: " + curso.getNome());
        System.out.println("--------------------------------------------------");

        System.out.println("Professor: " + professor.getNome());
        System.out.println("CPF: " + professor.getCpf());
        System.out.println("Email: " + professor.getEmail());
        System.out.println("Especialidade: " + professor.getEspecialidade());
        System.out.println("--------------------------------------------------");

        System.out.println("Disciplina: " + disciplinaOne.getNome());
        System.out.println("Carga Horária: " + disciplinaOne.getCargaHoraria() + " horas");
        System.out.println("--------------------------------------------------");

        System.out.println("Aluno: " + alunoOne.getNome());
        System.out.println("CPF: " + alunoOne.getCpf());
        System.out.println("Email: " + alunoOne.getEmail());
        System.out.println("Matrícula: " + alunoOne.getMatricula());
        System.out.println("--------------------------------------------------");

        System.out.println(alunoOne.consultarBoletim());

        System.out.println("--------------------------------------------------");

        System.out.println("\nAlunos matriculados no curso " + curso.getNome() + ":");
        if (curso.getListaAlunos() != null) {
            for (Aluno alunoMatriculas : curso.getListaAlunos()) {
                System.out.println("ID: " + alunoMatriculas.getId() + " | Nome: " + alunoMatriculas.getNome() + " | Matrícula: " + alunoMatriculas.getMatricula());
            }
        }
        System.out.println("--------------------------------------------------");

        System.out.println("\nDisciplinas do curso " + curso.getNome() + ":");
        if (curso.getListaDisciplinas() != null) {
            for (Disciplina disciplina1 : curso.getListaDisciplinas()) {
                System.out.println("Disciplina: " + disciplina1.getNome() + " | Carga Horária: " + disciplina1.getCargaHoraria() + " horas");
            }
        }
        System.out.println("--------------------------------------------------");

        sc.close();
    }

    // testarBancoDeDados(alunoDao, avaliacaoDao, cursoDao, disciplinaDao, matriculaDao, professorDao);

    private static void testarBancoDeDados(AlunoDao alunoDao, AvaliacaoDao avaliacaoDao,
                                           CursoDao cursoDao, DisciplinaDao disciplinaDao,
                                           MatriculaDao matriculaDao, ProfessorDao professorDao) {

        Avaliacao avaliacao = avaliacaoDao.findById(3);
        if (avaliacao != null) {
            avaliacao.setFrequencia(75);
            avaliacaoDao.update(avaliacao);
            System.out.println("ATUALIZADO COM SUCESSO!");
        }

        List<Avaliacao> avaliacoes = avaliacaoDao.findAll();
        for (Avaliacao a : avaliacoes) {
            System.out.println(a);
        }

        Disciplina disciplina = disciplinaDao.findById(2);
        if (disciplina != null) {
            avaliacoes = avaliacaoDao.findByDisciplina(disciplina);
            for (Avaliacao a : avaliacoes) {
                System.out.println(a);
            }
        }

        Aluno aluno = alunoDao.findById(2);
        if (aluno != null) {
            avaliacoes = avaliacaoDao.findByAluno(aluno);
            for (Avaliacao a : avaliacoes) {
                System.out.println(a);
            }
        }

        if (aluno != null && disciplina != null) {
            avaliacao = new Avaliacao(null, aluno, disciplina, 7.5, 75);
            avaliacaoDao.insert(avaliacao);
        }

        disciplina = disciplinaDao.findById(4);
        if (disciplina != null) {
            disciplina.setNome("Segurança de Dados");
            disciplinaDao.update(disciplina);
        }

        Professor professor = professorDao.findById(4);
        if (professor != null) {
            List<Disciplina> disciplinas = disciplinaDao.findByProfessor(professor);
            for (Disciplina d : disciplinas) {
                System.out.println(d);
            }
        }

        List<Disciplina> todasDisciplinas = disciplinaDao.findAll();
        for (Disciplina d : todasDisciplinas) {
            System.out.println(d);
        }

        Curso curso = cursoDao.findById(1);
        if (curso != null) {
            Professor prof = adicionarProfessorAoCurso("Ana Silva", "98765432200", "ana@email.com", "Redes de Computadores", curso);
            System.out.println("Professor inserido com ID: " + prof.getId());
        }

        List<Matricula> matriculas = matriculaDao.findAll();
        for (Matricula m : matriculas) {
            System.out.println(m);
        }

        List<Professor> professores = professorDao.findByEspecialidade("Python");
        for (Professor p : professores) {
            System.out.println(p);
        }
    }
}