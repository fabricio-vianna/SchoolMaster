package model.dao;

import java.util.List;

import model.entities.Curso;
import model.entities.Disciplina;
import model.entities.Professor;

public interface DisciplinaDao {

    void insert(Disciplina obj);

    void update(Disciplina obj);

    void deleteByID(Integer id);

    Disciplina findById(Integer id);

    List<Disciplina> findByCurso(Curso curso);

    Disciplina findByProfessor(Professor professor);

    List<Disciplina> findAll();
}
