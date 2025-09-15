package entities;

import java.util.List;

public class Disciplina {

    private Integer id;
    private String nome;
    private Integer cargaHoraria;
    private Professor professorResponsavel;
    private List<Avaliacao> listaAvaliacoes;

    public Disciplina(Integer id, String nome, Integer cargaHoraria, Professor professorResponsavel) {
        this.id = id;
        this.nome = nome;
        this.cargaHoraria = cargaHoraria;
        this.professorResponsavel = professorResponsavel;
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

    public Professor getProfessorResponsavel() {
        return professorResponsavel;
    }

    public void setProfessorResponsavel(Professor professorResponsavel) {
        this.professorResponsavel = professorResponsavel;
    }

    public List<Avaliacao> getListaAvaliacoes() {
        return listaAvaliacoes;
    }

    public void atribuirProfessor(Professor professor) {
        this.professorResponsavel = professor;
    }

    public void registrarAvaliacao(Avaliacao avaliacao) {
        listaAvaliacoes.add(avaliacao);
    }

    public List<Avaliacao> listarNotas() {
        return listaAvaliacoes;
    }
}
