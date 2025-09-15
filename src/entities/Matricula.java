package entities;

import java.time.LocalDate;

public class Matricula {

    private Integer id;
    private Aluno aluno;
    private Curso curso;
    private LocalDate dataMatricula;

    public Matricula(Integer id, Aluno aluno, Curso curso, LocalDate dataMatricula) {
        this.id = id;
        this.aluno = aluno;
        this.curso = curso;
        this.dataMatricula = dataMatricula;
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

    public void confirmarMatricula(){
        System.out.println("Matrícula confirmada para o aluno: " + aluno.getNome());
    }

    public void canclearmatricula() {
        System.out.println("matrícula cancelada para o aluno: " + curso.getNome());
    }
}