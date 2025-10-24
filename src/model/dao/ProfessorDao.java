package model.dao;

import java.util.List;

import model.entities.Aluno;
import model.entities.Disciplina;
import model.entities.Professor;

public interface ProfessorDao {

    void insert(Aluno obj);

    void update(Aluno obj);

    void deleteByID(Integer id);

    void findById(Integer id);

    void findByDisciplina(Disciplina obj);

    void findByEspecialidade(String especialidade);

    List<Professor> findAll();
}
