package persistencia;

import exceptions.*;
import dados.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class AvaliacaoDAO {
    private static AvaliacaoDAO instance = null;
    private PreparedStatement selectNewId;
    private PreparedStatement select;
    private PreparedStatement insert;
    private PreparedStatement delete;
    private PreparedStatement update;
    private PreparedStatement selectdisciplina;
    private PreparedStatement updatenota;

    public static AvaliacaoDAO getInstance() throws ClassNotFoundException, SQLException, SelectException {
        if (instance == null) {
            instance = new AvaliacaoDAO();
        }
        return instance;
    }

    public AvaliacaoDAO() throws ClassNotFoundException, SQLException, SelectException {

        Connection conexao = Conexao.getConexao();
        selectNewId = conexao.prepareStatement("select nextval('id_avaliacao')");
        insert = conexao.prepareStatement("Insert into avaliacao values(?,?,?,?,?,?)");
        select = conexao.prepareStatement("select * from avaliacao where id = ?");
        update = conexao.prepareStatement(
                "update avaliacao set peso = ?, nome = ?,dia = ? where id = ?");
        delete = conexao.prepareStatement("delete from avaliacao where id = ?");
        selectdisciplina = conexao.prepareStatement("select * from avaliacao where id_disciplina = ?");
        updatenota = conexao.prepareStatement(
                "update avaliacao set nota = ? where id = ?");
    }

    private int selectNewId() throws SelectException {

        try {
            ResultSet rs = selectNewId.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            throw new SelectException("Erro ao buscar novo id da tabela avaliacoes");
        }
        return 0;
    }

    public void insert(Avaliacao avaliacao) throws InsertException, SelectException {

        try {
            insert.setInt(1, selectNewId());
            insert.setDouble(2, avaliacao.getNota());
            insert.setDouble(3, avaliacao.getPeso());
            insert.setString(4, avaliacao.getNome());
            insert.setString(5, avaliacao.getData());
            insert.setInt(6, avaliacao.getId_disciplina());

            insert.executeUpdate();
        } catch (SQLException e) {
            throw new InsertException("Erro ao inserir avaliacao");
        }
    }

    public Avaliacao select(int avaliacao) throws InsertException, SelectException {
        try {
            select.setInt(1, avaliacao);
            ResultSet rs = select.executeQuery();
            if (rs.next()) {
                int id = rs.getInt(1);
                Double nota = rs.getDouble(2);
                Double peso = rs.getDouble(3);
                String nome = rs.getString(4);
                String data = rs.getString(5);
                int id_disciplina = rs.getInt(6);
                return new Avaliacao(id, nota, peso, nome, data, id_disciplina);
            }
        } catch (SQLException e) {
            throw new SelectException("Erro ao buscar avaliacao");
        }
        return null;
    }

    public void update(int avaliacao, double peso, String nome, String dia) throws UpdateException {
        try {
            select.setInt(1, avaliacao);
            ResultSet rs = select.executeQuery();
            if (rs.next()) {
                update.setDouble(1, peso);
                update.setString(2, nome);
                update.setString(3, dia);
                update.setInt(4, rs.getInt(1));
                update.executeUpdate();
            }
        } catch (SQLException e) {
            throw new UpdateException("Erro ao atualizar avaliacao");
        }
    }

    public void delete(int avaliacao) throws DeleteException {
        try {
            select.setInt(1, avaliacao);
            ResultSet rs = select.executeQuery();
            if (rs.next()) {
                delete.setInt(1, rs.getInt(1));
                delete.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DeleteException("Erro ao deletar avaliacao");
        }
    }

    public List<Avaliacao> selectDisciplina(int disciplina) throws SelectException {
        List<Avaliacao> avaliacoes = new LinkedList<Avaliacao>();
        try {
            selectdisciplina.setInt(1, disciplina);
            ResultSet rs = selectdisciplina.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                Double nota = rs.getDouble(2);
                Double peso = rs.getDouble(3);
                String nome = rs.getString(4);
                String data = rs.getString(5);
                int id_disciplina = rs.getInt(6);
                avaliacoes.add(new Avaliacao(id, nota, peso, nome, data, id_disciplina));
            }

        } catch (SQLException e) {
            throw new SelectException("Erro ao buscar avaliacoes");
        }
        return avaliacoes;
    }

    public void updatenota(int avaliacao, double nota) throws UpdateException {
        try {
            select.setInt(1, avaliacao);
            ResultSet rs = select.executeQuery();
            if (rs.next()) {
            updatenota.setDouble(1, nota);
            updatenota.setInt(2,  rs.getInt(1));
            updatenota.executeUpdate();
        }}  catch (SQLException e) {
            throw new UpdateException("Erro ao atualizar nota");
        }
    }

    public double calcularNota(int disciplina) throws SelectException {
        try {
            selectdisciplina.setInt(1, disciplina);
            ResultSet rs = selectdisciplina.executeQuery();
            double soma = 0.0;
            double peso = 0.0;
            while (rs.next()) {
                Double nota = rs.getDouble(2);
                soma += nota * rs.getDouble(3);
                peso += rs.getDouble(3);
            }
            return soma / peso;

        } catch (SQLException e) {
            throw new SelectException("Erro ao buscar avaliacoes");
        }
    }
}
