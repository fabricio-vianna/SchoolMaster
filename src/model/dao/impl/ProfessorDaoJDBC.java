package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.ProfessorDao;
import model.entities.Disciplina;
import model.entities.Professor;

public class ProfessorDaoJDBC implements ProfessorDao {

    private Connection conn;

    public ProfessorDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Professor obj) {
        PreparedStatement stPessoa = null;
        PreparedStatement stProfessor = null;
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

            stProfessor = conn.prepareStatement(
                    "INSERT INTO professor "
                            + "(id, especialidade) "
                            + "VALUES "
                            + "(?, ?)",
                    Statement.RETURN_GENERATED_KEYS);

            stProfessor.setInt(1, pessoaID);
            stProfessor.setString(2, obj.getEspecialidade());

            stProfessor.executeUpdate();

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
            DB.closeStatement(stProfessor);
            DB.closeResultSet(rs);
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void update(Professor obj) {
        PreparedStatement stPessoa = null;
        PreparedStatement stProfessor = null;

        try {
            conn.setAutoCommit(false);

            stPessoa = conn.prepareStatement(
                    "UPDATE pessoa "
                            + "SET nome = ?, cpf = ?, email = ? "
                            + "WHERE id = ?");

            stPessoa.setString(1, obj.getNome());
            stPessoa.setString(2, obj.getCpf());
            stPessoa.setString(3, obj.getEmail());
            stPessoa.setInt(4, obj.getId());

            stPessoa.executeUpdate();

            stProfessor = conn.prepareStatement(
                    "UPDATE professor "
                            + "SET especialidade = ? "
                            + "WHERE id = ?");

            stProfessor.setString(1, obj.getEspecialidade());
            stProfessor.setInt(2, obj.getId());

            stProfessor.executeUpdate();

            conn.commit();
        } catch (SQLException e) {
            try {
                conn.rollback();
                throw new DbException("Transação revertida! Erro: + e.getMessage()");
            } catch (SQLException e1) {
                throw new RuntimeException("Erro ao tentar reverter transação! " + e1.getMessage());
            }
        } finally {
            DB.closeStatement(stPessoa);
            DB.closeStatement(stProfessor);
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void deleteByID(Integer id) {

    }

    @Override
    public void findById(Integer id) {

    }

    @Override
    public void findByDisciplina(Disciplina obj) {

    }

    @Override
    public void findByEspecialidade(String especialidade) {

    }

    @Override
    public List<Professor> findAll() {
        return List.of();
    }
}
