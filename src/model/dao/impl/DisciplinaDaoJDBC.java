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
                obj.setId(rs.getInt(1));
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
        PreparedStatement st = null;

        try {
            conn.setAutoCommit(false);

            st = conn.prepareStatement(
                    "UPDATE disciplina " +
                            "SET nome = ?, carga_horaria = ?, id_professor = ?, id_curso = ? " +
                            "WHERE id = ?");

            st.setString(1, obj.getNome());
            st.setInt(2, obj.getCargaHoraria());
            st.setInt(3, obj.getProfessor().getId());
            st.setInt(4, obj.getCurso().getId());
            st.setInt(5, obj.getId());

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
            st = conn.prepareStatement(
                    "DELETE FROM disciplina "
                            + "WHERE "
                            + "id = ?");

            st.setInt(1, id);

            int rowsAffected = st.executeUpdate();

            if (rowsAffected == 0) {
                throw new DbException("Id inválido ou já deletado");
            }

        } catch (SQLException e) {
            throw new DbException("Erro ao tentar deletar disciplina! " + e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public Disciplina findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement(
                    "SELECT "
                            + "d.id AS disciplinaID, "
                            + "d.nome AS disciplinaNome, "
                            + "d.carga_horaria AS cargaHoraria, "
                            + "d.id_professor AS professorID, "
                            + "d.id_curso AS cursoID "
                            + "FROM disciplina d "
                            + "WHERE d.id = ?");

            st.setInt(1, id);

            rs = st.executeQuery();

            if (rs.next()) {
                Disciplina disciplina = instantiateDisciplina(rs);
                disciplina.setId(rs.getInt("disciplinaID"));
                return disciplina;
            }
            return null;

        } catch (SQLException e) {
            throw new DbException("Erro ao buscar disciplina por ID: " + e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Disciplina> findByCurso(Curso curso) {
        PreparedStatement st = null;
        ResultSet rs = null;

        List<Disciplina> list = new ArrayList<>();

        try {
            st = conn.prepareStatement(
                    "SELECT "
                            + "d.id AS disciplinaID, "
                            + "d.nome AS disciplinaNome, "
                            + "d.carga_horaria AS cargaHoraria, "
                            + "d.id_professor AS professorID, "
                            + "d.id_curso AS cursoID, "
                            + "c.nome AS cursoNome "
                            + "FROM disciplina d "
                            + "INNER JOIN curso c  "
                            + "ON d.id_curso = c.id "
                            + "WHERE c.id = ?");

            st.setInt(1, curso.getId());

            rs = st.executeQuery();

            while (rs.next()) {
                list.add(instantiateDisciplina(rs));
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
    public List<Disciplina> findByProfessor(Professor professor) {
        PreparedStatement st = null;
        ResultSet rs = null;

        List<Disciplina> list = new ArrayList<>();

        try {
            st = conn.prepareStatement(
                    "SELECT "
                            + "p.id AS professorID, "
                            + "p.nome AS professorNome, "
                            + "prof.especialidade, "
                            + "d.id AS disciplinaID, "
                            + "d.nome AS disciplinaNome, "
                            + "d.carga_horaria AS cargaHoraria, "
                            + "d.id_curso AS cursoID "
                            + "FROM pessoa p "
                            + "INNER JOIN professor prof ON p.id = prof.id "
                            + "INNER JOIN disciplina d ON d.id_professor = prof.id "
                            + "WHERE prof.id = ?");

            st.setInt(1, professor.getId());

            rs = st.executeQuery();

            while (rs.next()) {
                list.add(instantiateDisciplina(rs));
            }
            return list;

        } catch (SQLException e) {
            throw new DbException("Erro ao buscar disciplina por professor: " + e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Disciplina> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement(
                    "SELECT "
                            + "d.id AS disciplinaID, "
                            + "d.nome AS disciplinaNome, "
                            + "d.carga_horaria AS cargaHoraria, "
                            + "d.id_professor AS professorID, "
                            + "d.id_curso AS cursoID "
                            + "FROM disciplina d");

            rs = st.executeQuery();

            List<Disciplina> disciplinas = new ArrayList<>();

            while (rs.next()) {
                Disciplina disciplina = instantiateDisciplina(rs);
                disciplinas.add(disciplina);
            }

            return disciplinas;
        } catch (SQLException e) {
            throw new DbException("ERRO AO LISTAR AS DISCIPLINAS: " + e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    private Disciplina instantiateDisciplina(ResultSet rs) throws SQLException {
        String disciplinaNome = rs.getString("disciplinaNome");
        int cargaHoraria = rs.getInt("cargaHoraria");
        int professorID = rs.getInt("professorID");
        int cursoID = rs.getInt("cursoID");

        Professor professor = new ProfessorDaoJDBC(conn).findById(professorID);
        Curso curso = new CursoDaoJDBC(conn).findById(cursoID);

        Disciplina disciplina = new Disciplina(null, disciplinaNome, cargaHoraria, professor, curso);

        return disciplina;
    }
}
