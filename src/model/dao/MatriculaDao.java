package model.dao;

import java.util.List;

import model.entities.Aluno;
import model.entities.Curso;
import model.entities.Matricula;

public interface MatriculaDao {

    void insert(Matricula obj);

    void update(Matricula obj);

    void deleteByID(Integer id);

    Matricula findById(Integer id);

    List<Matricula> findByAluno(Aluno aluno);

    Matricula findByCurso(Curso curso);

    void cancelarMatricula(Integer id);

    List<Matricula> findAll();
}
