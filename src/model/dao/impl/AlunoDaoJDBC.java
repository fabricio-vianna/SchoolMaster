package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.AlunoDao;
import model.entities.Aluno;
import model.entities.Curso;

public class AlunoDaoJDBC implements AlunoDao {

    private Connection conn;

    public AlunoDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Aluno obj) {
        PreparedStatement stPessoa = null;
        PreparedStatement stAluno = null;
        ResultSet rs = null;

        try {
            conn.setAutoCommit(false);

            stPessoa = conn.prepareStatement(
                    "INSERT INTO pessoa "
                            + "(nome, cpf, email) "
                            + "VALUES "
                            + "(?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);

            stPessoa.setString(1, obj.getNome());
            stPessoa.setString(2, obj.getCpf());
            stPessoa.setString(3, obj.getEmail());

            int rowsAffected = stPessoa.executeUpdate();

            if (rowsAffected == 0) {
                throw new DbException("Falha ao inserir pessoa, nenhuma linha afetada.");
            }

            rs = stPessoa.getGeneratedKeys();
            int pessoaID;
            if (rs.next()) {
                pessoaID = rs.getInt(1);
            } else {
                throw new DbException("Falha ao inserir pessoa, nenhum ID obtido.");
            }

            stAluno = conn.prepareStatement(
                    "INSERT INTO aluno "
                            + "(id, matricula, id_curso) "
                            + "VALUES "
                            + "(?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);

            stAluno.setInt(1, pessoaID);
            stAluno.setString(2, obj.getMatricula());
            stAluno.setInt(3, obj.getCurso().getId());

            stAluno.executeUpdate();

            conn.commit();
        } catch (SQLException e) {
            try {
                conn.rollback();
                throw new DbException("Transação revertida! Erro: " + e.getMessage());
            } catch (SQLException e1) {
                throw new DbException("Erro ao tentar reverter transação! " + e1.getMessage());
            }
        } finally {
            DB.closeStatement(stPessoa);
            DB.closeStatement(stAluno);
            DB.closeResultSet(rs);
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void update(Aluno obj) {

    }

    @Override
    public void deleteByID(Integer id) {
        PreparedStatement stPessoa = null;
        PreparedStatement stAluno = null;

        try {
            conn.setAutoCommit(false);

            stAluno = conn.prepareStatement(
                    "DELETE FROM aluno "
                            + "WHERE "
                            + "id = ?");

            stAluno.setInt(1, id);
            stAluno.executeUpdate();

            stPessoa = conn.prepareStatement(
                    "DELETE FROM pessoa "
                            + "WHERE "
                            + "id = ?");

            stPessoa.setInt(1, id);

            int rowsAffected = stPessoa.executeUpdate();

            if (rowsAffected == 0) {
                throw new DbException("Id inválido ou já deletado");
            }

            conn.commit();
        } catch (SQLException e) {
            try {
                conn.rollback();
                throw new DbException("Transação revertida! Erro: " + e.getMessage());
            } catch (SQLException e1) {
                throw new DbException("Erro ao tentar reverter transação! " + e1.getMessage());
            }
        } finally {
            DB.closeStatement(stPessoa);
            DB.closeStatement(stAluno);
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
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
