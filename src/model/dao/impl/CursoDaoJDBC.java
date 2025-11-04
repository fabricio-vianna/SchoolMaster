package model.dao.impl;

import java.util.List;

import model.dao.CursoDao;
import model.entities.Aluno;
import model.entities.Professor;

public class CursoDaoJDBC implements CursoDao {
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
    public void findByNome(String nome) {

    }

    @Override
    public void findWithDisciplinas(Integer id) {

    }

    @Override
    public List<Professor> findAll() {
        return List.of();
    }
}
