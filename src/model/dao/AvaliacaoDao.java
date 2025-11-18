package model.dao;

import java.util.List;

import model.entities.Aluno;
import model.entities.Avaliacao;
import model.entities.Disciplina;
import model.entities.Professor;

public interface AvaliacaoDao {

    void insert(Avaliacao obj);

    void update(Avaliacao obj);

    void deleteByID(Integer id);

    Avaliacao findById(Integer id);

    List<Avaliacao> findByAluno(Aluno obj);

    List<Avaliacao> findByDisciplina(Disciplina obj);

    List<Avaliacao> findAll();
}
