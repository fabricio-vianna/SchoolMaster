package model.dao;

import java.util.List;

import model.entities.Disciplina;
import model.entities.Professor;

public interface ProfessorDao {

    void insert(Professor obj);

    void update(Professor obj);

    void deleteByID(Integer id);

    Professor findById(Integer id);

    List<Professor> findByDisciplina(Disciplina obj);

    void findByEspecialidade(String especialidade);

    List<Professor> findAll();
}
