package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        PreparedStatement stPessoa = null;
        PreparedStatement stProfessor = null;

        try {
            conn.setAutoCommit(false);

            stProfessor = conn.prepareStatement(
                    "DELETE FROM professor "
                            + "WHERE "
                            + "id = ?");

            stProfessor.setInt(1, id);
            stProfessor.executeUpdate();

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
            DB.closeStatement(stProfessor);
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Professor findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement(
                    "SELECT "
                            + "prof.id AS professorID, "
                            + "prof.especialidade, "
                            + "p.id AS pessoaID, "
                            + "p.nome AS pessoaNome, "
                            + "p.cpf, "
                            + "p.email "
                            + "FROM professor prof "
                            + "INNER JOIN pessoa p ON p.id = prof.id "
                            + "WHERE prof.id = ?");

            st.setInt(1, id);

            rs = st.executeQuery();

            if (rs.next()) {
                Professor professor = instantiateProfessor(rs);
                return professor;
            }
            return null;

        } catch (SQLException e) {
            throw new DbException("Erro ao buscar professor por ID: " + e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Professor> findByDisciplina(Disciplina obj) {
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Professor> professores = new ArrayList<>();

        try {
            st = conn.prepareStatement(
                    "SELECT "
                            + "prof.id AS professorID, "
                            + "prof.especialidade, "
                            + "p.id AS pessoaID, "
                            + "p.nome AS pessoaNome, "
                            + "p.cpf, "
                            + "p.email, "
                            + "d.id AS disciplinaID, "
                            + "d.nome AS disciplinaNome "
                            + "FROM professor prof "
                            + "INNER JOIN pessoa p ON p.id = prof.id "
                            + "INNER JOIN disciplina d ON d.id_professor = prof.id "
                            + "WHERE d.id = ? "
                            + "ORDER BY p.nome");

            st.setInt(1, obj.getId());
            rs = st.executeQuery();

            while (rs.next()) {
                Disciplina disciplina = instantiateDisciplina(rs);
                Professor professor = instantiateProfessor(rs);
                professor.atribuirDisciplina(disciplina);
                professores.add(professor);
            }

            return professores;

        } catch (SQLException e) {
            throw new DbException("Erro ao buscar professores por disciplina: " + e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Professor> findByEspecialidade(String especialidade) {
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Professor> professores = new ArrayList<>();

        try {
            st = conn.prepareStatement(
                    "SELECT "
                            + "prof.id AS professorID, "
                            + "prof.especialidade, "
                            + "p.id AS pessoaID, "
                            + "p.nome AS pessoaNome, "
                            + "p.cpf, "
                            + "p.email, "
                            + "d.id AS disciplinaID, "
                            + "d.nome AS disciplinaNome "
                            + "FROM professor prof "
                            + "INNER JOIN pessoa p ON p.id = prof.id "
                            + "INNER JOIN disciplina d ON d.id_professor = prof.id "
                            + "WHERE prof.especialidade = ? "
                            + "ORDER BY p.nome");

            st.setString(1, especialidade);
            rs = st.executeQuery();

            Map<Integer, Professor> map = new HashMap<>();

            while (rs.next()) {
                int profID = rs.getInt("professorID");

                Professor professor = map.get(profID);

                if (professor == null) {
                    professor = instantiateProfessor(rs);
                    map.put(profID, professor);
                    professores.add(professor);
                }

                Disciplina disciplina = instantiateDisciplina(rs);
                professor.atribuirDisciplina(disciplina);
            }

            return professores;

        } catch (SQLException e) {
            throw new DbException("Erro ao buscar professores por especialidade: " + e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Professor> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement(
                    "SELECT "
                            + "prof.id AS professorID,  "
                            + "prof.especialidade, "
                            + "p.id AS pessoaID, "
                            + "p.nome AS pessoaNome, "
                            + "p.cpf, p.email, "
                            + "d.id AS disciplinaID, "
                            + "d.nome AS disciplinaNome, "
                            + "d.carga_horaria "
                            + "FROM professor prof "
                            + "INNER JOIN pessoa p "
                            + "ON p.id = prof.id "
                            + "INNER JOIN disciplina d "
                            + "ON d.id_professor = prof.id");

            rs = st.executeQuery();

            List<Professor> professores = new ArrayList<>();
            Map<Integer, Professor> map = new HashMap<>();

            while (rs.next()) {
                int professorID = rs.getInt("professorID");
                Professor professor = map.get(professorID);

                if (professor == null) {
                    professor = instantiateProfessor(rs);
                    map.put(professorID, professor);
                    professores.add(professor);
                }

                int disciplinaID = rs.getInt("disciplinaID");
                if (disciplinaID > 0) {
                    Disciplina disciplina = instantiateDisciplina(rs);
                    professor.atribuirDisciplina(disciplina);
                }
            }

            return professores;
        } catch (SQLException e) {
            throw new DbException("ERRO AO LISTAR OS PROFESSORES: " + e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    private Professor instantiateProfessor(ResultSet rs) throws SQLException {
        Professor professor = new Professor();

        professor.setId(rs.getInt("professorID"));
        professor.setEspecialidade(rs.getString("especialidade"));
        professor.setNome(rs.getString("pessoaNome"));
        professor.setEmail(rs.getString("email"));
        professor.setCpf(rs.getString("cpf"));

        return professor;
    }

    private Disciplina instantiateDisciplina(ResultSet rs) throws SQLException {
        Disciplina disciplina = new Disciplina();

        disciplina.setId(rs.getInt("disciplinaID"));
        disciplina.setNome(rs.getString("disciplinaNome"));

        return disciplina;
    }
}
