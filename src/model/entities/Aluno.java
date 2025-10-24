package model.entities;

import java.util.List;
import java.util.stream.Collectors;

public class Aluno extends Pessoa {

    private String matricula;
    private Curso curso;

    public Aluno(Integer id, String nome, String cpf, String email) {
        super(id, nome, cpf, email);
    }

    public Aluno(Integer id, String nome, String cpf, String email, String matricula, Curso curso) {
        super(id, nome, cpf, email);
        this.matricula = matricula;
        this.curso = curso;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public Curso getCurso() {
        return curso;
    }

    public String consultarBoletim() {
        StringBuilder boletim = new StringBuilder();

        boletim.append("Boletim de ").append(this.getNome()).append("\n");
        boletim.append("Matrícula: ").append(this.getMatricula()).append("\n");
        boletim.append("--------------------------------------------------\n");

        for (Disciplina d : curso.getListaDisciplinas()) {
            List<Avaliacao> avaliacoes = d.getListaAvaliacoes().stream()
                    .filter(a -> a.getAluno().equals(this))
                    .collect(Collectors.toList());

            if (!avaliacoes.isEmpty()) {
                boletim.append("Disciplina: ").append(d.getNome()).append("\n");
                boletim.append("Carga Horária: ").append(d.getCargaHoraria()).append(" horas\n");

                for (Avaliacao avaliacao : avaliacoes) {
                    boletim.append("Nota: ").append(avaliacao.getNota()).append("\n");
                    boletim.append("Frequência: ").append(avaliacao.getFrequencia()).append("%\n");
                }

                boletim.append("--------------------------------------------------\n");
            }
        }

        return boletim.toString();
    }

    public List<Disciplina> listarDisciplinas() {
        return curso.getListaDisciplinas();
    }

    @Override
    public String toString() {
        return "Aluno { " +
                "id: " + getId() +
                ", nome: '" + getNome() + '\'' +
                ", cpf: " + getCpf() +
                ", email: '" + getEmail() + '\'' +
                ", matricula: '" + matricula + '\'' +
                ", curso: " + (curso != null ? curso.getNome() : "Sem curso") +
                '}';
    }
}
