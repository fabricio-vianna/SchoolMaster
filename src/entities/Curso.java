package entities;

import java.util.ArrayList;
import java.util.List;

public class Curso {

    private Integer id;
    private String nome;

    List<Disciplina> listaDisciplinas = new ArrayList<>();
    List<Aluno> listaAlunos = new ArrayList<>();

    public Curso(String nome, Integer id) {
        this.nome = nome;
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Disciplina> getListaDisciplinas() {
        return listaDisciplinas;
    }

    public List<Aluno> getListaAlunos() {
        return listaAlunos;
    }

    public void adicionarDisciplina(Disciplina disciplina) {
        listaDisciplinas.add(disciplina);
    }

    public void adicionarAluno(Aluno aluno) {
        listaAlunos.add(aluno);
    }

    public List<Aluno> listarAlunos() {
        return listaAlunos;
    }
}
