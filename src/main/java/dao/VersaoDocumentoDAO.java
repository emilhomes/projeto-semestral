package dao;

import model.VersaoDocumentoModel;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import conexao.ConexaoMySQL;

public class VersaoDocumentoDAO {
 

    public void inserir(VersaoDocumentoModel versao) {
    String sql = "INSERT INTO versaodocumento (idTCC, dataEnvio, nomeArquivo) VALUES (?, ?, ?)";

    try (Connection con = ConexaoMySQL.getConnection();
         PreparedStatement stmt = con.prepareStatement(sql)) {

        stmt.setInt(1, versao.getIdTCC());
        stmt.setDate(2, Date.valueOf(versao.getDataEnvio()));
        stmt.setString(3, versao.getNomeArquivo());  // <-- caminho completo

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
