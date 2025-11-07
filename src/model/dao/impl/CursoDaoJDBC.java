package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.CursoDao;
import model.entities.Curso;

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
        PreparedStatement st = null;

        try {
            conn.setAutoCommit(false);

            st = conn.prepareStatement(
                    "UPDATE curso "
                            + "SET nome = ? "
                            + "WHERE id = ?");

            st.setString(1, obj.getNome());
            st.setInt(2, obj.getId());

            st.executeUpdate();

            conn.commit();
        } catch (SQLException e) {
            try {
                conn.rollback();
                throw new DbException("Transação revertida! Erro: " + e.getMessage());
            } catch (SQLException e1) {
                throw new RuntimeException("Erro ao tentar reverter transação! " + e1.getMessage());
            }
        } finally {
            DB.closeStatement(st);
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
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
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement(
                    "SELECT "
                            + "c.id AS cursoID, "
                            + "c.nome AS cursoNome "
                            + "FROM curso c "
                            + "WHERE c.id = ?");

            st.setInt(1, id);

            rs = st.executeQuery();

            if (rs.next()) {
                Curso curso = instantiateCurso(rs);
                return curso;
            }
            return null;

        } catch (SQLException e) {
            throw new DbException("Erro ao buscar curso por ID: " + e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public Curso findByNome(String nome) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement(
                    "SELECT "
                            + "c.id AS cursoID, "
                            + "c.nome AS cursoNome "
                            + "FROM curso c "
                            + "WHERE c.nome = ?");

            st.setString(1, nome);

            rs = st.executeQuery();

            if (rs.next()) {
                Curso curso = instantiateCurso(rs);
                return curso;
            }
            return null;

        } catch (SQLException e) {
            throw new DbException("Erro ao buscar curso por nome: " + e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public Curso findWithDisciplinas(Integer id) {
        return null;
    }

    @Override
    public List<Curso> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement(
                    "SELECT "
                            + "c.id AS cursoID, "
                            + "c.nome AS cursoNome "
                            + "FROM curso c ");

            rs = st.executeQuery();

            List<Curso> cursos = new ArrayList<>();

            while (rs.next()) {
                Curso curso = new Curso(rs.getInt("cursoID"), rs.getString("cursoNome"));
                cursos.add(curso);
            }

            return cursos;
        } catch (SQLException e) {
            throw new DbException("ERRO AO LISTAR OS CURSOS: " + e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    private Curso instantiateCurso(ResultSet rs) throws SQLException {
        Curso curso = new Curso();

        curso.setId(rs.getInt("cursoID"));
        curso.setNome(rs.getString("cursoNome"));

        return curso;
    }
}
