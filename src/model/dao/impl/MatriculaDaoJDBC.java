package model.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

    }

    @Override
    public Matricula findById(Integer id) {
        return null;
    }

    @Override
    public Matricula findByAluno(Aluno aluno) {
        return null;
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
}
