package entities;

import java.util.List;

public class Professor extends Pessoa {

    private String especialidade;
    private List<Disciplina> listaDisciplinas;

    public Professor(Integer id, String nome, String cpf, String email) {
        super(id, nome, cpf, email);
    }

    public Professor(Integer id, String nome, String cpf, String email, String especialidade, Disciplina listaDisciplinas) {
        super(id, nome, cpf, email);
        this.especialidade = especialidade;
        this.listaDisciplinas = listaDisciplinas;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public List<Disciplina> getListaDisciplinas() {
        return listaDisciplinas;
    }

    public void setListaDisciplinas(List<Disciplina> listaDisciplinas) {
        this.listaDisciplinas = listaDisciplinas;
    }

    public void atribuirDisciplina(Disciplina disciplina) {
        listaDisciplinas.add(disciplina);
    }

    public List<Disciplina> listarDisciplinas() {
        return listaDisciplinas;
    }
}
