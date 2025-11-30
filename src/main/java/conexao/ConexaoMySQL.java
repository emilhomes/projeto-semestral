package conexao;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConexaoMySQL {
    private static final String URL = "jdbc:mysql://localhost:3306/projeto_semestral";
    private static final String USER = "root";
    private static final String PASS = "";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao conectar com o banco: ", e);
        }
    }
}