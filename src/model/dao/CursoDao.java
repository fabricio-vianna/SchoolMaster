package model.dao;

import java.util.List;

import model.entities.Curso;
import model.entities.Professor;

public interface CursoDao {

    void insert(Curso obj);

    void update(Curso obj);

    void deleteByID(Integer id);

    Curso findById(Integer id);

    Curso findByNome(String nome);

    Curso findWithDisciplinas(Integer id);

    List<Professor> findAll();
}
