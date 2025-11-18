package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.AvaliacaoDao;
import model.entities.Avaliacao;
import model.entities.Disciplina;
import model.entities.Professor;

public class AvaliacaoDaoJDBC implements AvaliacaoDao {

    private Connection conn;

    public AvaliacaoDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Avaliacao obj) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement(
                    "INSERT INTO avaliacao "
                            + "(id_aluno, id_disciplina, nota, frequencia) "
                            + "VALUES "
                            + "(?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);

            st.setInt(1, obj.getAluno().getId());
            st.setInt(2, obj.getDisciplina().getId());
            st.setDouble(3, obj.getNota());
            st.setInt(4, obj.getFrequencia());

            int rowsAffected = st.executeUpdate();

            if (rowsAffected == 0) {
                throw new DbException("Falha ao inserir avaliação, nenhuma linha afetada.");
            }

            rs = st.getGeneratedKeys();

            if (rs.next()) {
                int id = rs.getInt(1);
            } else {
                throw new DbException("Falha ao inserir avaliação, nenhum ID obtido.");
            }

        } catch (SQLException e) {
            throw new DbException("Erro ao tentar reverter transação! " + e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public void update(Avaliacao obj) {

    }

    @Override
    public void deleteByID(Integer id) {
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement(
                    "DELETE FROM avaliacao "
                            + "WHERE "
                            + "id = ?");

            st.setInt(1, id);

            int rowsAffected = st.executeUpdate();

            if (rowsAffected == 0) {
                throw new DbException("Id inválido ou já deletado");
            }

        } catch (SQLException e) {
            throw new DbException("Erro ao tentar deletar avaliação! " + e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public Avaliacao findById(Integer id) {
        return null;
    }

    @Override
    public Avaliacao findByAluno(Avaliacao aluno) {
        return null;
    }

    @Override
    public Avaliacao findByDisciplina(Disciplina disciplina) {
        return null;
    }

    @Override
    public List<Professor> findAll() {
        return List.of();
    }
}
