package dao;

import model.VersaoDocumentoModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import conexao.ConexaoMySQL;

public class VersaoDocumentoDAO {
 

    public void inserir(VersaoDocumentoModel versao) {
        String sql = "INSERT INTO versao(nomeArquivo, dataEnvio, idComentario) VALUES (?, ?, ?)";

        try (Connection conn = ConexaoMySQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, versao.getNomeArquivo());
            stmt.setDate(2, java.sql.Date.valueOf(versao.getDataEnvio()));
            stmt.setInt(3, versao.getIdComentario());
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
