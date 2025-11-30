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

    // CORREÇÃO: Método inserir limpo e unificado
    public void inserir(VersaoDocumentoModel versao) {
        // Ordem escolhida: idTCC, nomeArquivo, dataEnvio
        String sql = "INSERT INTO versaodocumento (idTCC, nomeArquivo, dataEnvio) VALUES (?, ?, ?)";

        try (Connection con = ConexaoMySQL.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, versao.getIdTCC());
            stmt.setString(2, versao.getNomeArquivo()); // Caminho do arquivo
            stmt.setDate(3, Date.valueOf(versao.getDataEnvio()));

            // O idComentario não é inserido agora, pois começa nulo (será atualizado pelo professor depois)

            stmt.executeUpdate();
            System.out.println("Versão do documento inserida com sucesso!");

        } catch (SQLException e) {
            System.err.println("Erro ao inserir versão do documento: " + e.getMessage());
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
                
                // Conversão segura de SQL Date para LocalDate
                if (rs.getDate("dataEnvio") != null) {
                    v.setDataEnvio(rs.getDate("dataEnvio").toLocalDate());
                }
                
                // Se precisar recuperar o idComentario no futuro:
                // v.setIdComentario(rs.getInt("idComentario"));

                lista.add(v);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    // Método wrapper (atalho) mantido
    public List<VersaoDocumentoModel> listarPorTcc(int idTcc) {
        return listarVersoesPorTcc(idTcc);
    }

    public void salvarComentario(VersaoDocumentoModel versao) {
        // Atualiza a tabela vinculando um comentário (FK) a esta versão
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