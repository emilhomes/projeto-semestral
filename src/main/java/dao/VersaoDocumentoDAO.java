package dao;

import model.VersaoDocumentoModel;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
<<<<<<< Updated upstream

=======
>>>>>>> Stashed changes
import conexao.ConexaoMySQL;

public class VersaoDocumentoDAO {

    // 1. INSERIR (O que você já tinha, mas ajustado para a tabela correta)
    public void inserir(VersaoDocumentoModel versao) {
        String sql = "INSERT INTO versaodocumento (idTCC, dataEnvio, nomeArquivo) VALUES (?, ?, ?)";
<<<<<<< Updated upstream

        try (Connection con = ConexaoMySQL.getConnection();
                PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, versao.getIdTCC());
            stmt.setDate(2, Date.valueOf(versao.getDataEnvio()));
            stmt.setString(3, versao.getNomeArquivo()); // <-- caminho completo
=======
        // Nome da tabela ajustado para 'versaodocumento' conforme sua imagem
        String sql = "INSERT INTO versaodocumento(nomeArquivo, dataEnvio, idTCC) VALUES (?, ?, ?)";

        try (Connection con = ConexaoMySQL.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, versao.getIdTCC());
            stmt.setDate(2, Date.valueOf(versao.getDataEnvio()));
            stmt.setString(3, versao.getNomeArquivo());  // <-- caminho completo

                stmt.setString(1, versao.getNomeArquivo());
                stmt.setDate(2, java.sql.Date.valueOf(versao.getDataEnvio()));
                stmt.setInt(3, versao.getIdTCC()); // Importante ligar ao TCC!
                
                // idComentario começa nulo ou 0, então não inserimos agora
                
                stmt.executeUpdate();

>>>>>>> Stashed changes

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
<<<<<<< Updated upstream

    public List<VersaoDocumentoModel> listarVersoesPorTcc(int idTcc) {
        List<VersaoDocumentoModel> lista = new ArrayList<>();

        String sql = "SELECT * FROM versaodocumento WHERE idTCC = ? ORDER BY dataEnvio DESC";

        try (Connection conn = ConexaoMySQL.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idTcc);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
=======
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
>>>>>>> Stashed changes

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
<<<<<<< Updated upstream
    }

    public List<VersaoDocumentoModel> listarPorTcc(int idTcc) {
        return listarVersoesPorTcc(idTcc);
    }
=======
}
>>>>>>> Stashed changes

    public void salvarComentario(VersaoDocumentoModel versao) {
        String sql = "UPDATE versaodocumento SET idComentario = ? WHERE idVersao = ?";

        try (Connection con = ConexaoMySQL.getConnection();
                PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, versao.getIdComentario());
            stmt.setInt(2, versao.getIdVersao());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
