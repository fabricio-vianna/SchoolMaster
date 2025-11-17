package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.DisciplinaDao;
import model.entities.Curso;
import model.entities.Disciplina;
import model.entities.Professor;

public class DisciplinaDaoJDBC implements DisciplinaDao {

    private Connection conn;

    public DisciplinaDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Disciplina obj) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement(
                    "INSERT INTO disciplina " +
                            "(nome, carga_horaria, id_professor, id_curso) " +
                            "VALUES (?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);

            st.setString(1, obj.getNome());
            st.setInt(2, obj.getCargaHoraria());
            st.setInt(3, obj.getProfessor().getId());
            st.setInt(4, obj.getCurso().getId());

            int rowsAffected = st.executeUpdate();

            if (rowsAffected == 0) {
                throw new DbException("Falha ao inserir disciplina, nenhuma linha afetada.");
            }

            rs = st.getGeneratedKeys();

            if (rs.next()) {
                int id = rs.getInt(1);
            } else {
                throw new DbException("Falha ao inserir disciplina, nenhum ID obtido.");
            }

        } catch (SQLException e) {
            throw new DbException("Erro ao tentar reverter transação! " + e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public void update(Disciplina obj) {

    }

    @Override
    public void deleteByID(Integer id) {

    }

    @Override
    public Disciplina findById(Integer id) {
        return null;
    }

    @Override
    public Disciplina findByCurso(Curso curso) {
        return null;
    }

    @Override
    public Disciplina findByProfessor(Professor professor) {
        return null;
    }

    @Override
    public List<Professor> findAll() {
        return List.of();
    }
}
