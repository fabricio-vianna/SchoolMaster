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
import model.dao.AlunoDao;
import model.entities.Aluno;
import model.entities.Curso;

public class AlunoDaoJDBC implements AlunoDao {

    private Connection conn;

    public AlunoDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Aluno obj) {
        PreparedStatement stPessoa = null;
        PreparedStatement stAluno = null;
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

            stAluno = conn.prepareStatement(
                    "INSERT INTO aluno "
                            + "(id, matricula, id_curso) "
                            + "VALUES "
                            + "(?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);

            stAluno.setInt(1, pessoaID);
            stAluno.setString(2, obj.getMatricula());
            stAluno.setInt(3, obj.getCurso().getId());

            stAluno.executeUpdate();

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
            DB.closeStatement(stAluno);
            DB.closeResultSet(rs);
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void update(Aluno obj) {
        PreparedStatement stPessoa = null;
        PreparedStatement stAluno = null;

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

            stAluno = conn.prepareStatement(
                    "UPDATE aluno "
                            + "SET matricula = ?, id_curso = ? "
                            + "WHERE id = ?");

            stAluno.setString(1, obj.getMatricula());
            stAluno.setInt(2, obj.getCurso().getId());
            stAluno.setInt(3, obj.getId());

            stAluno.executeUpdate();

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
            DB.closeStatement(stAluno);
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
        PreparedStatement stAluno = null;

        try {
            conn.setAutoCommit(false);

            stAluno = conn.prepareStatement(
                    "DELETE FROM aluno "
                            + "WHERE "
                            + "id = ?");

            stAluno.setInt(1, id);
            stAluno.executeUpdate();

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
            DB.closeStatement(stAluno);
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Aluno findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement(
                    "SELECT "
                            + "a.id AS alunoID, "
                            + "a.matricula, "
                            + "p.id AS pessoaID, "
                            + "p.nome AS pessoaNome, "
                            + "p.cpf, "
                            + "p.email, "
                            + "c.id AS cursoID, "
                            + "c.nome AS cursoNome "
                            + "FROM aluno a "
                            + "INNER JOIN pessoa p ON p.id = a.id "
                            + "INNER JOIN curso c ON c.id = a.id_curso "
                            + "WHERE a.id = ?");

            st.setInt(1, id);

            rs = st.executeQuery();

            if (rs.next()) {
                Curso curso = instantiateCurso(rs);
                Aluno aluno = instantiateAluno(rs, curso);
                return aluno;
            }
            return null;

        } catch (SQLException e) {
            throw new DbException("Erro ao buscar aluno por ID: " + e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Aluno> findByCurso(Curso curso) {
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Aluno> alunos = new ArrayList<>();

        try {
            st = conn.prepareStatement(
                    "SELECT "
                            + "a.id AS alunoID, "
                            + "a.matricula, "
                            + "p.id AS pessoaID, "
                            + "p.nome AS pessoaNome, "
                            + "p.cpf, "
                            + "p.email, "
                            + "c.id AS cursoID, "
                            + "c.nome AS cursoNome "
                            + "FROM aluno a "
                            + "INNER JOIN pessoa p ON p.id = a.id "
                            + "INNER JOIN curso c ON c.id = a.id_curso "
                            + "WHERE c.id = ? "
                            + "ORDER BY p.nome");

            st.setInt(1, curso.getId());

            rs = st.executeQuery();

            while (rs.next()) {
                Curso c = instantiateCurso(rs);
                Aluno aluno = instantiateAluno(rs, c);
                alunos.add(aluno);
            }

            return alunos;

        } catch (SQLException e) {
            throw new DbException("Erro ao buscar alunos por curso: " + e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Aluno> findByNome(String nome) {
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Aluno> alunos = new ArrayList<>();

        try {
            st = conn.prepareStatement(
                    "SELECT "
                            + "a.id AS alunoID, "
                            + "a.matricula, "
                            + "p.id AS pessoaID, "
                            + "p.nome AS pessoaNome, "
                            + "p.cpf, "
                            + "p.email, "
                            + "c.id AS cursoID, "
                            + "c.nome AS cursoNome "
                            + "FROM aluno a "
                            + "INNER JOIN pessoa p ON p.id = a.id "
                            + "INNER JOIN curso c ON c.id = a.id_curso "
                            + "WHERE p.nome = ?");

            st.setString(1, nome);

            rs = st.executeQuery();

            while (rs.next()) {
                Curso curso = instantiateCurso(rs);
                Aluno aluno = instantiateAluno(rs, curso);
                alunos.add(aluno);
            }

            return alunos;

        } catch (SQLException e) {
            throw new DbException("Erro ao buscar aluno por nome: " + e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Aluno> findAll() {
        return List.of();
    }

    private Aluno instantiateAluno(ResultSet rs, Curso curso) throws SQLException {
        Aluno aluno = new Aluno();

        aluno.setId(rs.getInt("alunoID"));
        aluno.setMatricula(rs.getString("matricula"));
        aluno.setNome(rs.getString("pessoaNome"));
        aluno.setEmail(rs.getString("email"));
        aluno.setCpf(rs.getString("cpf"));
        aluno.setCurso(curso);

        return aluno;
    }

    private Curso instantiateCurso(ResultSet rs) throws SQLException {
        Curso curso = new Curso();

        curso.setId(rs.getInt("cursoID"));
        curso.setNome(rs.getString("cursoNome"));

        return curso;
    }
}
