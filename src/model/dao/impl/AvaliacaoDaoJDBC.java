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
import model.dao.AvaliacaoDao;
import model.entities.Aluno;
import model.entities.Avaliacao;
import model.entities.Disciplina;

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
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement(
                    "SELECT "
                            + "a.id AS avaliacaoID, "
                            + "a.id_aluno AS alunoID, "
                            + "a.id_disciplina AS disciplinaID, "
                            + "a.nota, "
                            + "a.frequencia "
                            + "FROM avaliacao a "
                            + "WHERE id = ?");

            st.setInt(1, id);

            rs = st.executeQuery();

            if (rs.next()) {
                Avaliacao avaliacao = instantiateAvaliacao(rs);
                avaliacao.setId(rs.getInt("avaliacaoID"));
                return avaliacao;
            }
            return null;

        } catch (SQLException e) {
            throw new DbException("Erro ao buscar avaliação por ID: " + e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Avaliacao> findByAluno(Aluno obj) {
        PreparedStatement st = null;
        ResultSet rs = null;

        List<Avaliacao> list = new ArrayList<>();

        try {
            st = conn.prepareStatement(
                    "SELECT "
                            + "a.id_aluno AS alunoID, "
                            + "p.nome AS alunoNome, "
                            + "a.id AS avaliacaoID, "
                            + "a.id_disciplina AS disciplinaID, "
                            + "a.nota, "
                            + "a.frequencia "
                            + "FROM avaliacao a "
                            + "INNER JOIN pessoa p ON p.id = a.id_aluno "
                            + "WHERE p.id = ?");

            st.setInt(1, obj.getId());

            rs = st.executeQuery();

            while (rs.next()) {
                list.add(instantiateAvaliacao(rs));
            }
            return list;

        } catch (SQLException e) {
            throw new DbException("Erro ao buscar disciplina por curso: " + e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Avaliacao> findByDisciplina(Disciplina obj) {
        PreparedStatement st = null;
        ResultSet rs = null;

        List<Avaliacao> list = new ArrayList<>();

        try {
            st = conn.prepareStatement(
                    "SELECT "
                            + "d.id AS disciplinaID, "
                            + "d.nome AS disciplinaNome, "
                            + "a.id AS avaliacaoID, "
                            + "a.id_aluno AS alunoID, "
                            + "a.nota, "
                            + "a.frequencia "
                            + "FROM disciplina d "
                            + "INNER JOIN avaliacao a  "
                            + "ON d.id = a.id_disciplina "
                            + "WHERE d.id = ?");

            st.setInt(1, obj.getId());

            rs = st.executeQuery();

            while (rs.next()) {
                list.add(instantiateAvaliacao(rs));
            }
            return list;

        } catch (SQLException e) {
            throw new DbException("Erro ao buscar avaliação por disciplina: " + e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Avaliacao> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement(
                    "SELECT "
                            + "a.id AS avaliacaoID, "
                            + "a.id_aluno AS alunoID, "
                            + "a.id_disciplina AS disciplinaID, "
                            + "a.nota, "
                            + "a.frequencia "
                            + "FROM avaliacao a ");

            rs = st.executeQuery();

            List<Avaliacao> list = new ArrayList<>();

            while (rs.next()) {
                Avaliacao avaliacao = instantiateAvaliacao(rs);
                list.add(avaliacao);
            }

            return list;
        } catch (SQLException e) {
            throw new DbException("ERRO AO LISTAR AS AVALIAÇÕES: " + e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    private Avaliacao instantiateAvaliacao(ResultSet rs) throws SQLException {
        int alunoID = rs.getInt("alunoID");
        int disciplinaID = rs.getInt("disciplinaID");
        double nota = rs.getDouble("nota");
        int frequencia = rs.getInt("frequencia");

        Aluno aluno = new AlunoDaoJDBC(conn).findById(alunoID);
        Disciplina disciplina = new DisciplinaDaoJDBC(conn).findById(disciplinaID);

        Avaliacao avaliacao = new Avaliacao(null, aluno, disciplina, nota, frequencia);
        return avaliacao;
    }
}
