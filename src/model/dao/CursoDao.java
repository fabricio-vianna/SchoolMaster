package model.dao;

import java.util.List;

import model.entities.Aluno;
import model.entities.Professor;

public interface CursoDao {

    void insert(Aluno obj);

    void update(Aluno obj);

    void deleteByID(Integer id);

    void findById(Integer id);

    void findByNome(String nome);

    void findWithDisciplinas(Integer id);

    List<Professor> findAll();
}
