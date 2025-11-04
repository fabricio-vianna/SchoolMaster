package application;

import java.util.Locale;
import java.util.Scanner;

import model.entities.Aluno;
import model.entities.Avaliacao;
import model.entities.Curso;
import model.entities.Disciplina;
import model.entities.Professor;
import services.CursoService;

public class Program {

    public static void main(String[] args) {

        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);

        System.out.print("Digite o nome do curso 1: ");
        String nomeCurso = sc.nextLine();

        Curso cursoOne = CursoService.criarCurso(1, nomeCurso);

        System.out.println("\nDigite os dados do Professor 1:");
        System.out.print("Nome: ");
        String nomeProfessor = sc.nextLine();

        System.out.print("CPF: ");
        String cpfProfessor = sc.nextLine();

        System.out.print("Email: ");
        String emailProfessor = sc.nextLine();

        System.out.print("Especialidade: ");
        String esplProfessor = sc.nextLine();

        Professor professorOne = CursoService.adicionarProfessorAoCurso(1, nomeProfessor, cpfProfessor, emailProfessor, esplProfessor, cursoOne);

        System.out.println("\nDigite os dados da Disciplina 1: ");
        System.out.print("Nome da disciplina: ");
        String nomeDisciplina = sc.nextLine();

        System.out.print("Carga horária (em horas): ");
        int hrDisciplina = sc.nextInt();
        sc.nextLine();

        Disciplina disciplinaOne = CursoService.adicionarDisciplinaAoCurso(1, nomeDisciplina, hrDisciplina, professorOne, cursoOne);

        System.out.println("\nDigite os dados do Aluno 1:");
        System.out.print("Nome: ");
        String nomeAluno = sc.nextLine();

        System.out.print("CPF: ");
        String cpfAluno = sc.nextLine();

        System.out.print("Email: ");
        String emailAluno = sc.nextLine();

        System.out.print("matrícula: ");
        String mtlAluno = sc.nextLine();

        Aluno alunoOne = CursoService.adicionarAlunoAoCurso(1, nomeAluno, cpfAluno, emailAluno, mtlAluno, cursoOne);

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
        } while (frqcAluno <= 0 && frqcAluno >= 100);
        sc.nextLine();

        Avaliacao avaliacaoOne = CursoService.registrarAvaliacao(1, alunoOne, disciplinaOne, notaAluno, frqcAluno);

        System.out.println("\nCurso: " + cursoOne.getNome());
        System.out.println("--------------------------------------------------");

        System.out.println("Professor: " + professorOne.getNome());
        System.out.println("CPF: " + professorOne.getCpf());
        System.out.println("Email: " + professorOne.getEmail());
        System.out.println("Especialidade: " + professorOne.getEspecialidade());
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

        System.out.println("\nAlunos matriculados no curso " + cursoOne.getNome() + ":");
        for (Aluno aluno : cursoOne.getListaAlunos()) {
            System.out.println("ID: " + aluno.getId() + " | Nome: " + aluno.getNome() + " | Matrícula: " + aluno.getMatricula());
        }
        System.out.println("--------------------------------------------------");

        System.out.println("\nDisciplinas do curso " + cursoOne.getNome() + ":");
        for (Disciplina disciplina : cursoOne.getListaDisciplinas()) {
            System.out.println("Disciplina: " + disciplina.getNome() + " | Carga Horária: " + disciplina.getCargaHoraria() + " horas");
        }
        System.out.println("--------------------------------------------------");

        sc.close();
    }
}
