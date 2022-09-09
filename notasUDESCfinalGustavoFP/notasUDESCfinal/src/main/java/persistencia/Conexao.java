package persistencia;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    private static Connection con = null;

    private Conexao() { // ninguem fora da classe consegue construir objeto desse tipo

    }

    public static Connection getConexao() {
        if(con == null){
            String usuario = "postgres";
            String senha ="1Sansastarktop";
            String url = "jdbc:postgresql://localhost:5432/notas10";

            try{ 
            Class.forName("org.postgresql.Driver");
           
        } catch (ClassNotFoundException c) {
            System.err.println("Driver não encontrado");}
        try{
            con = DriverManager.getConnection(url, usuario, senha);
        }
        
        catch (SQLException e) {
            System.err.println("Não foi possível conectar");
        }
                                                                            // pc da escola
        }
        return con; 

    }

}