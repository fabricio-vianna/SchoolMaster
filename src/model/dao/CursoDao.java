package model.dao;

import java.util.List;

import model.entities.Curso;

public interface CursoDao {

    void insert(Curso obj);

    void update(Curso obj);

    void deleteByID(Integer id);

    Curso findById(Integer id);

    Curso findByNome(String nome);

    Curso findWithDisciplinas(Integer id);

    List<Curso> findAll();
}
