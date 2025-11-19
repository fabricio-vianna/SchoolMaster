package model.entities;

import java.util.Objects;

public class Avaliacao {

    private Integer id;
    private Aluno aluno;
    private Disciplina disciplina;
    private Double nota;
    private Integer frequencia;

    public Avaliacao(Integer id, Aluno aluno, Disciplina disciplina, Double nota, Integer frequencia) {
        if (nota < 0.0 || nota > 10.0) {
            throw new IllegalArgumentException("Nota deve ser entre 0 e 10");
        }

        if (frequencia < 0.0 || frequencia > 100) {
            throw new IllegalArgumentException("Frequência deve estar entre 0% e 100 %");
        }

        this.id = id;
        this.aluno = aluno;
        this.disciplina = disciplina;
        this.nota = nota;
        this.frequencia = frequencia;
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

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    public Double getNota() {
        return nota;
    }

    public void setNota(Double nota) {
        this.nota = nota;
    }

    public Integer getFrequencia() {
        return frequencia;
    }

    public void setFrequencia(Integer frequencia) {
        this.frequencia = frequencia;
    }

    public void registrarNota(Double nota) {
        this.nota = nota;
    }

    public void registrarFrequencia(Integer frequencia) {
        this.frequencia = frequencia;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Avaliacao avaliacao = (Avaliacao) o;
        return Objects.equals(id, avaliacao.id) && Objects.equals(aluno, avaliacao.aluno) && Objects.equals(disciplina, avaliacao.disciplina);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, aluno, disciplina);
    }

    @Override
    public String toString() {
        return "Disciplina: " + disciplina.getNome() + "\n" +
                "Nota: " + String.format("%.1f", nota) + "\n" +
                "Frequência: " + frequencia + "%" + "\n" +
                "Nome do aluno: " + (aluno != null && aluno.getNome() != null ? aluno.getNome() + "\n" : "Desconhecido" + "\n") +
                "Matrícula: " + (aluno != null && aluno.getMatricula() != null ? aluno.getMatricula() : "Sem matrícula");
    }
}
