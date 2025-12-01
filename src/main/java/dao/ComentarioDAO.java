package dao;

import model.ComentarioModel;
import conexao.ConexaoMySQL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ComentarioDAO {

    public void inserir(ComentarioModel c) {
        // Agora usando idTcc e dataCriacao que adicionamos
        String sql = "INSERT INTO comentario (usuario, conteudo, idTcc, dataCriacao) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConexaoMySQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, c.getUsuario());
            stmt.setString(2, c.getConteudo());
            stmt.setInt(3, c.getIdTCC()); // Link direto com o TCC
            
            if (c.getData() != null) {
                stmt.setTimestamp(4, Timestamp.valueOf(c.getData()));
            } else {
                stmt.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
            }

            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<ComentarioModel> listarPorTcc(int idTcc) {
        List<ComentarioModel> lista = new ArrayList<>();
        
        // Busca comentários ligados diretamente ao TCC
        String sql = "SELECT * FROM comentario WHERE idTcc = ? ORDER BY dataCriacao ASC";

        try (Connection conn = ConexaoMySQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idTcc);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ComentarioModel c = new ComentarioModel();
                c.setIdComentario(rs.getInt("idComentario"));
                c.setUsuario(rs.getString("usuario"));
                c.setConteudo(rs.getString("conteudo"));
                c.setIdTCC(rs.getInt("idTcc"));
                
                Timestamp ts = rs.getTimestamp("dataCriacao");
                if(ts != null) c.setData(ts.toLocalDateTime());

                lista.add(c);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
}