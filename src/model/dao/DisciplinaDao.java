package model.dao;

import java.util.List;

import model.entities.Aluno;
import model.entities.Curso;
import model.entities.Professor;

public interface DisciplinaDao {

    void insert(Aluno obj);

    void update(Aluno obj);

    void deleteByID(Integer id);

    void findById(Integer id);

    void findByCurso(Curso curso);

    void findByProfessor(Professor professor);

    List<Professor> findAll();
}
