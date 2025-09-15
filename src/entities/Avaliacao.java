package entities;

public class Avaliacao {

    private Integer id;
    private Aluno aluno;
    private Disciplina disciplina;
    private Double nota;
    private Integer frequencia;

    public Avaliacao(Integer id, Aluno aluno, Disciplina disciplina, Double nota, Integer frequencia) {
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
}
