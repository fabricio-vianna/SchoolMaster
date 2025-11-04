package model.dao.impl;

import java.util.List;

import model.dao.AlunoDao;
import model.entities.Aluno;
import model.entities.Curso;

public class AlunoDaoJDBC implements AlunoDao {
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
    public void findByCurso(Curso curso) {

    }

    @Override
    public void findByNome(String nome) {

    }

    @Override
    public List<Aluno> findAll() {
        return List.of();
    }
}
