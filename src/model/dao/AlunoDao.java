package model.dao;

import java.util.List;

import model.entities.Aluno;
import model.entities.Curso;

public interface AlunoDao {

    void insert(Aluno obj);

    void update(Aluno obj);

    void deleteByID(Integer id);

    Aluno findById(Integer id);

    Aluno findByCurso(Curso curso);

    Aluno findByNome(String nome);

    List<Aluno> findAll();
}
