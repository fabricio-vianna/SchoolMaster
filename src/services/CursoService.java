package services;

import java.util.ArrayList;
import java.util.List;

import entities.Aluno;
import entities.Avaliacao;
import entities.Curso;
import entities.Disciplina;
import entities.Professor;

public class CursoService {

    private static List<Curso> cursosCriados = new ArrayList<>();

    private CursoService() {
    }

    public static Disciplina adicionarDisciplinaAoCurso(Integer id, String nome, Integer cargaHoraria, Professor professor, Curso curso) {
        if (professor == null || curso == null) {
            throw new IllegalArgumentException("Curso e Professor não podem ser nulos.");
        }

        Disciplina d = new Disciplina(id, nome, cargaHoraria, professor);
        curso.adicionarDisciplina(d);
        professor.atribuirDisciplina(d);
        return d;
    }

    public static boolean existe(Integer id, String nome) {
        return cursosCriados.stream()
                .anyMatch(c -> c.getId().equals(id) || c.getNome().equalsIgnoreCase(nome));
    }

    public static Curso criarCurso(Integer id, String nome) {
        if (existe(id, nome)) {
            throw new IllegalArgumentException("Curso com esse ID ou nome já existe!");
        }
        Curso curso = new Curso(id, nome);
        cursosCriados.add(curso);
        return curso;
    }

    public static Aluno adicionarAlunoAoCurso(Integer id, String nome, String cpf, String email, String matricula, Curso curso) {
        Aluno aluno = new Aluno(id, nome, cpf, email, matricula, curso);
        if (!curso.getListaAlunos().contains(aluno)) {
            curso.adicionarAluno(aluno);
        }
        return aluno;
    }

    public static Professor adicionarProfessorAoCurso(Integer id, String nome, String cpf, String email, String especialidade, Curso curso) {
        Professor professor = new Professor(id, nome, cpf, email, especialidade, curso);
        if (!curso.getListaProfessor().contains(professor)) {
            curso.adicionarProfessor(professor);
        }
        return professor;
    }

    public static Avaliacao registrarAvaliacao(Integer id, Aluno aluno, Disciplina disciplina, Double nota, Integer frequencia) {
        Avaliacao avaliacao = new Avaliacao(id, aluno, disciplina, nota, frequencia);
        disciplina.registrarAvaliacao(avaliacao);
        return avaliacao;
    }
}
