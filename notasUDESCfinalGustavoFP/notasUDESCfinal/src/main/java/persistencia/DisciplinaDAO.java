package persistencia;

import exceptions.*;
import dados.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class DisciplinaDAO {
    private static DisciplinaDAO instance = null;
    private PreparedStatement selectNewId;
    private PreparedStatement select;
    private PreparedStatement insert;
    private PreparedStatement delete;
    private PreparedStatement update;
    private PreparedStatement selectSemestre;

    public static DisciplinaDAO getInstance() throws ClassNotFoundException, SQLException, SelectException {
        if (instance == null) {
            instance = new DisciplinaDAO();
        }
        return instance;
    }

    public DisciplinaDAO() throws ClassNotFoundException, SQLException, SelectException {

        Connection conexao = Conexao.getConexao();
        selectNewId = conexao.prepareStatement("select nextval('id_disciplina')");
        insert = conexao.prepareStatement("Insert into disciplina values(?,?,?)");
        select = conexao.prepareStatement("select * from disciplina where id = ?");
        update = conexao.prepareStatement("update disciplina set nome = ?, id_semestre = ? where id = ?");
        delete = conexao.prepareStatement("delete from disciplina where id = ?");
        selectSemestre = conexao.prepareStatement("select * from disciplina where id_semestre = ?");
    }

    private int selectNewId() throws SelectException {

        try {
            ResultSet rs = selectNewId.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            throw new SelectException("Erro ao buscar novo id da tabela disciplina");
        }
        return 0;
    }

    public void insert(Disciplina disciplina) throws InsertException, SelectException {

        try {
            insert.setInt(1, selectNewId());
            insert.setString(2, disciplina.getNome());
            insert.setInt(3, disciplina.getId_semestre());
            insert.executeUpdate();
        } catch (SQLException e) {
            throw new InsertException("Erro ao inserir disciplina");
        }
    }

    public Disciplina select(int disciplina) throws InsertException, SelectException {
        try {
            select.setInt(1, disciplina);
            ResultSet rs = select.executeQuery();
            if (rs.next()) {
                int id = rs.getInt(1);
                String nome = rs.getString(2);
                int id_semestre = rs.getInt(3);
                return new Disciplina(id, nome, id_semestre);
            }
        } catch (SQLException e) {
            throw new SelectException("Erro ao buscar disciplina");
        }
        return null;
    }

    public void update(int disciplina, String nome) throws UpdateException {
        try {
            select.setInt(1, disciplina);
            ResultSet rs = select.executeQuery();
            if (rs.next()) {
            update.setString(1, nome);
            update.setInt(2, rs.getInt(3));
            update.setInt(3, rs.getInt(1));
            update.executeUpdate();
        }} catch (SQLException e) {
            throw new UpdateException("Erro ao atualizar disciplina");
        }
    }

    public void delete(int disciplina) throws DeleteException {
        try {
            select.setInt(1, disciplina);
            ResultSet rs = select.executeQuery();
            if (rs.next()) {
            delete.setInt(1, rs.getInt(1));
            delete.executeUpdate();
        }} catch (SQLException e) {
            throw new DeleteException("Erro ao deletar disciplina");
        }
    }

    public List<Disciplina> selectSemestre(int semestre) throws SelectException{
        List<Disciplina> disciplinas = new LinkedList<Disciplina>();
        try {
            selectSemestre.setInt(1, semestre);
            ResultSet rs = selectSemestre.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String nome = rs.getString(2);
                int id_semestre = rs.getInt(3);            
                disciplinas.add(new Disciplina(id, nome, id_semestre));
            }
        } catch (SQLException e) {
            throw new SelectException("Erro ao buscar disciplinas");
        }
        return disciplinas;
    }
    }