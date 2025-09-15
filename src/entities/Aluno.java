package entities;

import java.util.ArrayList;
import java.util.List;

public class Aluno extends Pessoa {

    private String matricula;
    private Curso curso;

    public Aluno(Integer id, String nome, String cpf, String email) {
        super(id, nome, cpf, email);
    }

    public Aluno(Integer id, String nome, String cpf, String email, String matricula, Curso curso) {
        super(id, nome, cpf, email);
        this.matricula = matricula;
        this.curso = curso;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public List<Avaliacao> consultarBoletim() {
        List<Avaliacao> boletim = new ArrayList<>();
        for(Disciplina d : curso.getListaDisciplinas()) {
            boletim.addAll(d.getListaAvaliacoes().stream().filter(a -> a.getAluno().equals(this)).toList());
        }
        return boletim;
    }

    public List<Disciplina> listarDisciplinas() {
        return curso.getListaDisciplinas();
    }
}
