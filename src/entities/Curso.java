package entities;

import java.util.ArrayList;
import java.util.List;

public class Curso {

    private Integer id;
    private String nome;

    private List<Disciplina> listaDisciplinas = new ArrayList<>();
    private List<Aluno> listaAlunos = new ArrayList<>();

    public Curso(Integer id, String nome) {
        this.id = id;
        this.nome = nome;
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
        if (!listaDisciplinas.contains(disciplina)) {
            listaDisciplinas.add(disciplina);
        }
    }

    public void removerDisciplina(Disciplina disciplina) {
        listaDisciplinas.remove(disciplina);
    }

    public void adicionarAluno(Aluno aluno) {
        if (!listaAlunos.contains(aluno)) {
            listaAlunos.add(aluno);
        }
    }

    public void removerAluno(Aluno aluno) {
        listaAlunos.remove(aluno);
    }

    public List<Aluno> listarAlunos() {
        return listaAlunos;
    }
}
