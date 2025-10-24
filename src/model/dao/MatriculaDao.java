package model.dao;

import java.util.List;

import model.entities.Aluno;
import model.entities.Curso;
import model.entities.Professor;

public interface MatriculaDao {

    void insert(Aluno obj);

    void update(Aluno obj);

    void deleteByID(Integer id);

    void findById(Integer id);

    void findByAluno(Aluno aluno);

    void findByCurso(Curso curso);

    void cancelarMatricula(Integer id);

    List<Professor> findAll();
}
