package model.entities;

import java.util.ArrayList;
import java.util.List;

public class Professor extends Pessoa {

    private String especialidade;
    private List<Disciplina> listaDisciplinas = new ArrayList<>();

    private Curso curso;

    public Professor(Integer id, String nome, String cpf, String email) {
        super(id, nome, cpf, email);
    }

    public Professor(Integer id, String nome, String cpf, String email, String especialidade, Curso curso) {
        super(id, nome, cpf, email);
        this.especialidade = especialidade;
        this.curso = curso;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public List<Disciplina> getListaDisciplinas() {
        return listaDisciplinas;
    }

    public void atribuirDisciplina(Disciplina disciplina) {
        listaDisciplinas.add(disciplina);
    }

    public List<Disciplina> listarDisciplinas() {
        return listaDisciplinas;
    }

    public Curso getCurso() {
        return curso;
    }


}
