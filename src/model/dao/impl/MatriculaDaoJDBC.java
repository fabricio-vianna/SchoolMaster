package model.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.MatriculaDao;
import model.entities.Aluno;
import model.entities.Curso;
import model.entities.Matricula;

public class MatriculaDaoJDBC implements MatriculaDao {

    private Connection conn;

    public MatriculaDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Matricula obj) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement(
                    "INSERT INTO matricula "
                            + "(id_aluno, id_curso, data_matricula, ativa) "
                            + "VALUES "
                            + "(?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);

            st.setInt(1, obj.getAluno().getId());
            st.setInt(2, obj.getCurso().getId());
            st.setDate(3, Date.valueOf(obj.getDataMatricula()));
            st.setBoolean(4, obj.isAtiva());

            int rowsAffected = st.executeUpdate();

            if (rowsAffected == 0) {
                throw new DbException("Falha ao inserir matricula, nenhuma linha afetada.");
            }

            rs = st.getGeneratedKeys();

            if (rs.next()) {
                int id = rs.getInt(1);
            } else {
                throw new DbException("Falha ao inserir matricula, nenhum ID obtido.");
            }

        } catch (SQLException e) {
            throw new DbException("Erro ao tentar reverter transação! " + e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public void update(Matricula obj) {

    }

    @Override
    public void deleteByID(Integer id) {
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement(
                    "DELETE FROM matricula "
                            + "WHERE "
                            + "id = ?");

            st.setInt(1, id);

            int rowsAffected = st.executeUpdate();

            if (rowsAffected == 0) {
                throw new DbException("Id inválido ou já deletado");
            }

        } catch (SQLException e) {
            throw new DbException("Erro ao tentar deletar matricula! " + e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public Matricula findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement(
                    "SELECT "
                            + "m.id AS matriculaID, "
                            + "m.id_aluno, "
                            + "m.id_curso, "
                            + "m.data_matricula, "
                            + "m.ativa "
                            + "FROM matricula m "
                            + "WHERE m.id = ?");

            st.setInt(1, id);

            rs = st.executeQuery();

            if (rs.next()) {
                Matricula matricula = instantiateMatricula(rs);
                return matricula;
            }
            return null;

        } catch (SQLException e) {
            throw new DbException("Erro ao buscar matricula por ID: " + e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Matricula> findByAluno(Aluno aluno) {
        PreparedStatement st = null;
        ResultSet rs = null;

        List<Matricula> list = new ArrayList<>();

        try {
            st = conn.prepareStatement(
                    "SELECT "
                            + "id AS matriculaID, "
                            + "id_aluno, "
                            + "id_curso, "
                            + "data_matricula, "
                            + "ativa "
                            + "FROM matricula "
                            + "WHERE id_aluno = ?");

            st.setInt(1, aluno.getId());

            rs = st.executeQuery();

            while (rs.next()) {
                list.add(instantiateMatricula(rs));
            }
            return list;

        } catch (SQLException e) {
            throw new DbException("Erro ao buscar matricula por aluno: " + e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public Matricula findByCurso(Curso curso) {
        return null;
    }

    @Override
    public void cancelarMatricula(Integer id) {

    }

    @Override
    public List<Matricula> findAll() {
        return List.of();
    }

    private Matricula instantiateMatricula(ResultSet rs) throws SQLException {
        int id = rs.getInt("matriculaID");
        int idAluno = rs.getInt("id_aluno");
        int idCurso = rs.getInt("id_curso");
        LocalDate dataMatricula = rs.getDate("data_matricula").toLocalDate();
        boolean ativa = rs.getBoolean("ativa");

        Aluno aluno = new AlunoDaoJDBC(conn).findById(idAluno);
        Curso curso = new CursoDaoJDBC(conn).findById(idCurso);

        Matricula matricula = new Matricula(id, aluno, curso, dataMatricula, ativa);
        return matricula;
    }
}
