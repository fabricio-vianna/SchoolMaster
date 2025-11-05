package model.dao;

import java.util.List;

import model.entities.Avaliacao;
import model.entities.Disciplina;
import model.entities.Professor;

public interface AvaliacaoDao {

    void insert(Avaliacao obj);

    void update(Avaliacao obj);

    void deleteByID(Integer id);

    Avaliacao findById(Integer id);

    Avaliacao findByAluno(Avaliacao aluno);

    Avaliacao findByDisciplina(Disciplina disciplina);

    List<Professor> findAll();
}
