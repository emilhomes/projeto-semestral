package dao;

import model.VersaoDocumentoModel;
import conexao.ConexaoMySQL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VersaoDocumentoDAO {

    // 1. INSERIR (O que você já tinha, mas ajustado para a tabela correta)
    public void inserir(VersaoDocumentoModel versao) {
        // Nome da tabela ajustado para 'versaodocumento' conforme sua imagem
        String sql = "INSERT INTO versaodocumento(nomeArquivo, dataEnvio, idTCC) VALUES (?, ?, ?)";

        try (Connection conn = ConexaoMySQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, versao.getNomeArquivo());
            stmt.setDate(2, java.sql.Date.valueOf(versao.getDataEnvio()));
            stmt.setInt(3, versao.getIdTCC()); // Importante ligar ao TCC!
            
            // idComentario começa nulo ou 0, então não inserimos agora
            
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 2. LISTAR (Com JOIN para pegar o texto do comentário)
    public List<VersaoDocumentoModel> listarPorTcc(int idTcc) {
        List<VersaoDocumentoModel> lista = new ArrayList<>();
        
        // Fazemos um LEFT JOIN com a tabela 'comentario'.
        // Isso significa: "Traga a versão, e SE tiver comentário, traga o texto dele também."
        String sql = "SELECT v.*, c.texto AS textoComentario " +
                     "FROM versaodocumento v " +
                     "LEFT JOIN comentario c ON v.idComentario = c.idComentario " +
                     "WHERE v.idTCC = ?";

        try (Connection conn = ConexaoMySQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idTcc);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                VersaoDocumentoModel v = new VersaoDocumentoModel();
                v.setIdVersao(rs.getInt("idVersao"));
                v.setIdTCC(rs.getInt("idTCC"));
                v.setNomeArquivo(rs.getString("nomeArquivo")); 
                
                Date data = rs.getDate("dataEnvio");
                if (data != null) v.setDataEnvio(data.toLocalDate());
                
                // Pega o ID do comentário (chave estrangeira)
                int idComent = rs.getInt("idComentario");
                v.setIdComentario(idComent);
                
                // O PULO DO GATO: Pega o texto que veio da outra tabela e salva no Model
                v.setTextoComentario(rs.getString("textoComentario"));

                lista.add(v);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    // 3. SALVAR COMENTÁRIO (Lógica Inteligente)
    public void salvarComentario(VersaoDocumentoModel versao) {
        try (Connection conn = ConexaoMySQL.getConnection()) {
            
            // Lógica de decisão: Criar novo ou Atualizar existente?
            if (versao.getIdComentario() > 0) {
                // CENÁRIO A: Já existe um comentário vinculado -> Fazemos UPDATE na tabela comentario
                String sqlUpdate = "UPDATE comentario SET texto = ? WHERE idComentario = ?";
                try (PreparedStatement stmt = conn.prepareStatement(sqlUpdate)) {
                    stmt.setString(1, versao.getTextoComentario());
                    stmt.setInt(2, versao.getIdComentario());
                    stmt.executeUpdate();
                }
            } else {
                // CENÁRIO B: Não existe comentário -> INSERT na tabela comentario + UPDATE na tabela versao
                
                // Passo 1: Inserir o texto na tabela 'comentario'
                String sqlInsert = "INSERT INTO comentario (texto) VALUES (?)";
                int novoIdGerado = 0;
                
                // Precisamos da flag RETURN_GENERATED_KEYS para saber qual ID foi criado
                try (PreparedStatement stmt = conn.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS)) {
                    stmt.setString(1, versao.getTextoComentario());
                    stmt.executeUpdate();
                    
                    ResultSet rs = stmt.getGeneratedKeys();
                    if (rs.next()) {
                        novoIdGerado = rs.getInt(1); // Pegamos o ID novo (ex: 15)
                    }
                }
                
                // Passo 2: Vincular esse novo ID na tabela 'versaodocumento'
                if (novoIdGerado > 0) {
                    String sqlLink = "UPDATE versaodocumento SET idComentario = ? WHERE idVersao = ?";
                    try (PreparedStatement stmt = conn.prepareStatement(sqlLink)) {
                        stmt.setInt(1, novoIdGerado);
                        stmt.setInt(2, versao.getIdVersao());
                        stmt.executeUpdate();
                    }
                    
                    // Atualiza o objeto Java para que na próxima vez ele caia no Cenário A
                    versao.setIdComentario(novoIdGerado); 
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}