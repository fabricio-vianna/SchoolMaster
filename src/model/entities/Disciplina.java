package model.entities;

import java.util.ArrayList;
import java.util.List;

public class Disciplina {

    private Integer id;
    private String nome;
    private Integer cargaHoraria;
    private Professor professor;
    private Curso curso;
    private List<Avaliacao> listaAvaliacoes;

    public Disciplina() {
    }

    public Disciplina(Integer id, String nome, Integer cargaHoraria, Professor professor, Curso curso) {
        this.id = id;
        this.nome = nome;
        this.cargaHoraria = cargaHoraria;
        this.professor = professor;
        this.curso = curso;

        this.listaAvaliacoes = new ArrayList<>();
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

    public Integer getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(Integer cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public List<Avaliacao> getListaAvaliacoes() {
        return listaAvaliacoes;
    }

    public void registrarAvaliacao(Avaliacao avaliacao) {
        if (!listaAvaliacoes.contains(avaliacao)) {
            listaAvaliacoes.add(avaliacao);
        }
    }

    public List<Avaliacao> listarNotas() {
        return listaAvaliacoes;
    }


    @Override
    public String toString() {
        return nome;
    }
}
