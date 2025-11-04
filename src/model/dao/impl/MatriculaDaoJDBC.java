package model.dao.impl;

import java.util.List;

import model.dao.MatriculaDao;
import model.entities.Aluno;
import model.entities.Curso;
import model.entities.Professor;

public class MatriculaDaoJDBC implements MatriculaDao {
    @Override
    public void insert(Aluno obj) {

    }

    @Override
    public void update(Aluno obj) {

    }

    @Override
    public void deleteByID(Integer id) {

    }

    @Override
    public void findById(Integer id) {

    }

    @Override
    public void findByAluno(Aluno aluno) {

    }

    @Override
    public void findByCurso(Curso curso) {

    }

    @Override
    public void cancelarMatricula(Integer id) {

    }

    @Override
    public List<Professor> findAll() {
        return List.of();
    }
}
