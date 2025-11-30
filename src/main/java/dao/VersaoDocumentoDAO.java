package dao;

import model.VersaoDocumentoModel;
<<<<<<< HEAD
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

=======
>>>>>>> 6c781109fc0027d5190bcbce64c34959453aa117
import conexao.ConexaoMySQL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VersaoDocumentoDAO {

    // 1. INSERIR (O que você já tinha, mas ajustado para a tabela correta)
    public void inserir(VersaoDocumentoModel versao) {
<<<<<<< HEAD
    String sql = "INSERT INTO versaodocumento (idTCC, dataEnvio, nomeArquivo) VALUES (?, ?, ?)";
=======
        // Nome da tabela ajustado para 'versaodocumento' conforme sua imagem
        String sql = "INSERT INTO versaodocumento(nomeArquivo, dataEnvio, idTCC) VALUES (?, ?, ?)";
>>>>>>> 6c781109fc0027d5190bcbce64c34959453aa117

    try (Connection con = ConexaoMySQL.getConnection();
         PreparedStatement stmt = con.prepareStatement(sql)) {

<<<<<<< HEAD
        stmt.setInt(1, versao.getIdTCC());
        stmt.setDate(2, Date.valueOf(versao.getDataEnvio()));
        stmt.setString(3, versao.getNomeArquivo());  // <-- caminho completo
=======
            stmt.setString(1, versao.getNomeArquivo());
            stmt.setDate(2, java.sql.Date.valueOf(versao.getDataEnvio()));
            stmt.setInt(3, versao.getIdTCC()); // Importante ligar ao TCC!
            
            // idComentario começa nulo ou 0, então não inserimos agora
            
            stmt.executeUpdate();
>>>>>>> 6c781109fc0027d5190bcbce64c34959453aa117

        stmt.executeUpdate();

    } catch (SQLException e) {
        e.printStackTrace();
    }
}
public List<VersaoDocumentoModel> listarVersoesPorTcc(int idTcc) {
    List<VersaoDocumentoModel> lista = new ArrayList<>();

    String sql = "SELECT * FROM versaodocumento WHERE idTCC = ? ORDER BY dataEnvio DESC";

    try (Connection conn = ConexaoMySQL.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, idTcc);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {

            VersaoDocumentoModel v = new VersaoDocumentoModel();
            v.setIdVersao(rs.getInt("idVersao"));
            v.setIdTCC(rs.getInt("idTCC"));
            v.setNomeArquivo(rs.getString("nomeArquivo"));
            v.setDataEnvio(rs.getDate("dataEnvio").toLocalDate());


            lista.add(v);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return lista;
}


}
