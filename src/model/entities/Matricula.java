package model.entities;

import java.time.LocalDate;

public class Matricula {

    private Integer id;
    private Aluno aluno;
    private Curso curso;
    private LocalDate dataMatricula;
    private boolean ativa;

    public Matricula() {
    }

    public Matricula(Integer id, Aluno aluno, Curso curso, LocalDate dataMatricula, boolean ativa) {
        this.id = id;
        this.aluno = aluno;
        this.curso = curso;
        this.dataMatricula = dataMatricula;
        this.ativa = ativa;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public LocalDate getDataMatricula() {
        return dataMatricula;
    }

    public void setDataMatricula(LocalDate dataMatricula) {
        this.dataMatricula = dataMatricula;
    }

    public void confirmarMatricula() {
        this.ativa = true;
    }

    public void cancelarMatricula() {
        this.ativa = false;
    }

    public boolean isAtiva() {
        return ativa;
    }
}