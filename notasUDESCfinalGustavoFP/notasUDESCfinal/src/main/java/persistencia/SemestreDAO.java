package persistencia;

import exceptions.*;
import dados.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class SemestreDAO {
    private static SemestreDAO instance = null;
    private PreparedStatement selectNewId;
    private PreparedStatement select;
    private PreparedStatement insert;
    private PreparedStatement selectAluno;
    private PreparedStatement delete;
    private PreparedStatement update;

    public static SemestreDAO getInstance() throws ClassNotFoundException, SQLException, SelectException {
        if (instance == null) {
            instance = new SemestreDAO();
        }
        return instance;
    }

    public SemestreDAO() throws ClassNotFoundException, SQLException, SelectException {

        Connection conexao = Conexao.getConexao();
        selectNewId = conexao.prepareStatement("select nextval('id_semestre')");
        insert = conexao.prepareStatement("Insert into semestre values(?,?,?)");
        select = conexao.prepareStatement("select * from semestre where id = ?");
        update = conexao.prepareStatement("update semestre set num_semestre = ?, id_aluno = ? where id = ?");
        delete = conexao.prepareStatement("delete from semestre where id = ?");
        selectAluno = conexao.prepareStatement("select * from semestre where id_aluno = ?");
    }

    private int selectNewId() throws SelectException {

        try {
            ResultSet rs = selectNewId.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            throw new SelectException("Erro ao buscar novo id da tabela semestre");
        }
        return 0;
    }

    public void insert(Semestre semestre) throws InsertException, SelectException {

        try {
            insert.setInt(1, selectNewId());
            insert.setString(2, semestre.getNumSemestre());
            insert.setInt(3, semestre.getId_aluno());
            insert.executeUpdate();
        } catch (SQLException e) {
            throw new InsertException("Erro ao inserir semestre");
        }
    }

    public Semestre select(int semestre) throws InsertException, SelectException {
        try {
            select.setInt(1, semestre);
            ResultSet rs = select.executeQuery();
            if (rs.next()) {
                int id = rs.getInt(1);
                String nome = rs.getString(2);
                int id_semestre = rs.getInt(3);
                return new Semestre(id, nome, id_semestre);
            }
        } catch (SQLException e) {
            throw new SelectException("Erro ao buscar semestre");
        }
        return null;
    }

    public void update(int semestre, String nome) throws UpdateException {
        try {
            select.setInt(1, semestre);
            ResultSet rs = select.executeQuery();
            if (rs.next()) {
                update.setString(1, nome);
                update.setInt(2, rs.getInt(3));
                update.setInt(3, rs.getInt(1));
                update.executeUpdate();
            }
        } catch (SQLException e) {
            throw new UpdateException("Erro ao atualizar semestre");
        }
    }

    public void delete(int semestre) throws DeleteException {
        try {
            select.setInt(1, semestre);
            ResultSet rs = select.executeQuery();
            if (rs.next()) {
                System.out.println(rs.getInt(1));

                delete.setInt(1, rs.getInt(1));
                
                delete.executeUpdate();
            }
            
        } catch (SQLException e) {
            throw new DeleteException("Erro ao deletar semestre");
        }
    }

    public List<Semestre> selectAluno(int aluno) throws SelectException {

        List<Semestre> semestres = new LinkedList<Semestre>();
        try {
            selectAluno.setInt(1, aluno);
            ResultSet rs = selectAluno.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String nome = rs.getString(2);
                int id_semestre = rs.getInt(3);

                semestres.add(new Semestre(id, nome, id_semestre));
            }

        } catch (SQLException e) {
            throw new SelectException("Erro ao buscar semestres");
        }
        return semestres;
    }
}
