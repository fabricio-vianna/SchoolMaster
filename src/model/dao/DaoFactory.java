package model.dao;

import db.DB;
import model.dao.impl.AlunoDaoJDBC;
import model.dao.impl.AvaliacaoDaoJDBC;
import model.dao.impl.CursoDaoJDBC;
import model.dao.impl.DisciplinaDaoJDBC;
import model.dao.impl.MatriculaDaoJDBC;
import model.dao.impl.ProfessorDaoJDBC;
import model.entities.Disciplina;
import model.entities.Professor;

public class DaoFactory {

    public static AlunoDao createAlunoDao() {
        return new AlunoDaoJDBC(DB.getConnection());
    }

    public static AvaliacaoDao createAvaliacaoDao() {
        return new AvaliacaoDaoJDBC(DB.getConnection());
    }

    public static CursoDao createCursoDao() {
        return new CursoDaoJDBC(DB.getConnection());
    }

    public static Disciplina createDisciplinaDao() {
        return new DisciplinaDaoJDBC(DB.getConnection());
    }

    public static MatriculaDao createMatriculaDao() {
        return new MatriculaDaoJDBC(DB.getConnection());
    }

    public static Professor createProfessorDao() {
        return new ProfessorDaoJDBC(DB.getConnection());
    }
}