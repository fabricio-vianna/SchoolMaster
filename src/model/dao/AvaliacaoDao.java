package model.dao;

import java.util.List;

import model.entities.Aluno;
import model.entities.Disciplina;
import model.entities.Professor;

public interface AvaliacaoDao {

    void insert(Aluno obj);

    void update(Aluno obj);

    void deleteByID(Integer id);

    void findById(Integer id);

    void findByAluno(Aluno aluno);

    void findByDisciplina(Disciplina disciplina);

    List<Professor> findAll();
}
