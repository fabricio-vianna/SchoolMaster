package services;

import java.util.ArrayList;
import java.util.List;

import entities.Aluno;
import entities.Avaliacao;
import entities.Curso;
import entities.Disciplina;
import entities.Professor;

public class CursoService {

    public static Disciplina criarDisciplinaNoCurso(Integer id, String nome, Integer cargaHoraria, Professor professor, Curso curso) {
        if (professor == null || curso == null) {
            throw new IllegalArgumentException("Curso e Professor não podem ser nulos.");
        }

        Disciplina d = new Disciplina(id, nome, cargaHoraria, professor);
        curso.adicionarDisciplina(d);
        professor.atribuirDisciplina(d);
        return d;
    }

    private static List<Curso> cursosCriados = new ArrayList<>();

    public static Curso criarCurso(Integer id, String nome) {
        boolean existe = cursosCriados.stream()
                .anyMatch(c -> c.getId().equals(id) || c.getNome().equalsIgnoreCase(nome));

        if (existe) {
            throw new IllegalArgumentException("Curso com esse ID ou nome já existe!");
        }
        Curso curso = new Curso(id, nome);
        cursosCriados.add(curso);

        return curso;
    }

    public static Aluno criarAluno(Integer id, String nome, String cpf, String email, String matricula, Curso curso) {
        Aluno aluno = new Aluno(id, nome, cpf, email, matricula, curso);
        if (!curso.getListaAlunos().contains(aluno)) {
            curso.adicionarAluno(aluno);
        }
        return aluno;
    }

    public static Avaliacao registrarAvaliacao(Integer id, Aluno aluno, Disciplina disciplina, Double nota, Integer frequencia) {
        Avaliacao avaliacao = new Avaliacao(id, aluno, disciplina, nota, frequencia);
        disciplina.registrarAvaliacao(avaliacao);
        return avaliacao;
    }
}
