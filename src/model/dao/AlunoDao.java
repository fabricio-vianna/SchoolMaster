package model.dao;

import java.util.List;

import model.entities.Aluno;
import model.entities.Curso;

public interface AlunoDao {

    void insert(Aluno obj);

    void update(Aluno obj);

    void deleteByID(Integer id);

    void findById(Integer id);

    void findByCurso(Curso curso);

    void findByNome(String nome);

    List<Aluno> findAll();
}
