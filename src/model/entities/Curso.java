package model.entities;

import java.util.ArrayList;
import java.util.List;

public class Curso {

    private Integer id;
    private String nome;

    private List<Disciplina> listaDisciplinas = new ArrayList<>();
    private List<Aluno> listaAlunos = new ArrayList<>();
    private List<Professor> listaProfessor = new ArrayList<>();

    public Curso(Integer id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Curso() {
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

    public void setListaDisciplinas(List<Disciplina> listaDisciplinas) {
        this.listaDisciplinas = listaDisciplinas;
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

    public List<Professor> getListaProfessor() {
        return listaProfessor;
    }

    public void adicionarProfessor(Professor professor) {
        if (!listaProfessor.contains(professor)) {
            listaProfessor.add(professor);
        }
    }

    public void removerProfessor(Professor professor) {
        listaProfessor.remove(professor);
    }

    public List<Professor> listarProfessor() {
        return listaProfessor;
    }

    @Override
    public String toString() {
        return "Curso{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", listaDisciplinas=" + listaDisciplinas +
                ", listaAlunos=" + listaAlunos +
                ", listaProfessor=" + listaProfessor +
                '}';
    }
}
