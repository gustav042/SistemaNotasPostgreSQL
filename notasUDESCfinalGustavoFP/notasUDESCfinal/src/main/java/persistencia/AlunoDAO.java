package persistencia;

import exceptions.*;
import dados.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AlunoDAO {
    private static AlunoDAO instance = null;
    private PreparedStatement selectNewId;
    private PreparedStatement select;
    private PreparedStatement insert;
    private PreparedStatement delete;
    private PreparedStatement update;
    private PreparedStatement login;
    private PreparedStatement jaCad;


    public static AlunoDAO getInstance() throws ClassNotFoundException, SQLException, SelectException {
        if (instance == null) {
            instance = new AlunoDAO();
        }
        return instance;
    }

    public AlunoDAO() throws ClassNotFoundException, SQLException, SelectException {

        Connection conexao = Conexao.getConexao();
        selectNewId = conexao.prepareStatement("select nextval('id_aluno')");
        insert = conexao.prepareStatement("Insert into aluno values(?,?,?,?,?)");
        select = conexao.prepareStatement("select * from aluno where id = ?");
        update = conexao.prepareStatement("update aluno set nome = ?, cpf = ?, senha = ?, curso = ?, where id = ?");
        delete = conexao.prepareStatement("delete from aluno where id = ?");
        login = conexao.prepareStatement("select * from aluno where cpf = ? and senha = ?");
        jaCad = conexao.prepareStatement("select * from aluno where cpf=?");

    }

    private int selectNewId() throws SelectException {

        try {
            ResultSet rs = selectNewId.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            throw new SelectException("Erro ao buscar novo id da tabela aluno");
        }
        return 0;
    }

    public void insert(Aluno aluno) throws InsertException, SelectException {

        try {
            insert.setInt(1, selectNewId());
            insert.setString(2, aluno.getNome());
            insert.setString(3, aluno.getCpf());
            insert.setString(4, aluno.getSenha());
            insert.setString(5, aluno.getCurso());
            insert.executeUpdate();
        } catch (SQLException e) {
            throw new InsertException("Erro ao inserir aluno");
        }
    }

    public Aluno select(int aluno) throws InsertException, SelectException {
        try {
            select.setInt(1, aluno);
            ResultSet rs = select.executeQuery();
            if (rs.next()) {
                int id = rs.getInt(1);
                String nome = rs.getString(2);
                String cpf = rs.getString(3);
                String senha = rs.getString(4);
                String curso = rs.getString(5);
                return new Aluno(id, nome, cpf, senha, curso);
            }
        } catch (SQLException e) {
            throw new SelectException("Erro ao buscar aluno");
        }
        return null;
    }

    public void update(int aluno) throws UpdateException {
        try {
            select.setInt(1, aluno);
            ResultSet rs = select.executeQuery();
            if (rs.next()) {
            update.setString(1, rs.getString(2));
            update.setString(2, rs.getString(3));
            update.setString(3, rs.getString(4));
            update.setString(4,rs.getString(5));
            update.setInt(1, rs.getInt(1));
            update.executeUpdate();}
        } catch (SQLException e) {
            throw new UpdateException("Erro ao atualizar aluno");
        }
    }

    public void delete(int aluno) throws DeleteException {
        try {
            select.setInt(1, aluno);
            ResultSet rs = select.executeQuery();
            if (rs.next()) {
            delete.setInt(1, rs.getInt(1));
            delete.executeUpdate();
        }} catch (SQLException e) {
            throw new DeleteException("Erro ao deletar aluno");
        }
    }

    public Aluno login(String cpf_, String senha_) throws InsertException, SelectException {
        try {
            login.setString(1, cpf_);
            login.setString(2, senha_);
            ResultSet rs = login.executeQuery();
            if (rs.next()) {
                int id = rs.getInt(1);
                String nome = rs.getString(2);
                String cpf = rs.getString(3);
                String senha = rs.getString(4);
                String curso = rs.getString(5);
                return new Aluno(id, nome, cpf, senha, curso);
            }
        } catch (SQLException e) {
            throw new SelectException("Erro ao buscar aluno");
        }
        return null;

}
    public void jaCadastrado(String cpf) throws JaCadastradoException{
        try {
            jaCad.setString(1, cpf);
            ResultSet rs = jaCad.executeQuery();
            if (rs.next()) {
                throw new JaCadastradoException("Aluno ja cadastrado");}
    }catch(SQLException e){}}
}