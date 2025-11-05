package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.CursoDao;
import model.entities.Curso;
import model.entities.Professor;

public class CursoDaoJDBC implements CursoDao {

    private Connection conn;

    public CursoDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Curso obj) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement(
                    "INSERT INTO curso "
                            + "(nome) "
                            + "VALUES "
                            + "(?)",
                    Statement.RETURN_GENERATED_KEYS);

            st.setString(1, obj.getNome());

            int rowsAffected = st.executeUpdate();

            if (rowsAffected == 0) {
                throw new DbException("Falha ao inserir curso, nenhuma linha afetada.");
            }

            rs = st.getGeneratedKeys();

            if (rs.next()) {
                int id = rs.getInt(1);
            } else {
                throw new DbException("Falha ao inserir curso, nenhum ID obtido.");
            }

        } catch (SQLException e) {
            throw new DbException("Erro ao tentar reverter transação! " + e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public void update(Curso obj) {

    }

    @Override
    public void deleteByID(Integer id) {
        PreparedStatement st = null;

        try {
            conn.setAutoCommit(false);

            st = conn.prepareStatement(
                    "DELETE FROM curso "
                            + "WHERE "
                            + "id = ?");

            st.setInt(1, id);
            st.executeUpdate();

            int rowsAffected = st.executeUpdate();

            if (rowsAffected == 0) {
                throw new DbException("Id inválido ou já deletado");
            }

        } catch (SQLException e) {
            throw new DbException("Erro ao tentar deletar curso! " + e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public Curso findById(Integer id) {
        return null;
    }

    @Override
    public Curso findByNome(String nome) {
        return null;
    }

    @Override
    public Curso findWithDisciplinas(Integer id) {
        return null;
    }

    @Override
    public List<Professor> findAll() {
        return List.of();
    }
}
