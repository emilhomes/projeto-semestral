package dao;

import model.TccModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import conexao.ConexaoMySQL;

public class TccDAO {

      public void inserir(TccModel tcc) {
        String sql = "INSERT INTO tcc (titulo, resumo, estado, dataCadastro, idAluno, idOrientador, idBanca, idVersao) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexaoMySQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, tcc.getTitulo());
            stmt.setString(2, tcc.getResumo());
            stmt.setString(3, tcc.getEstado());
            stmt.setDate(4, java.sql.Date.valueOf(tcc.getDataCadastro()));
            stmt.setInt(5, tcc.getIdAluno());
            stmt.setInt(6, tcc.getIdOrientador());

            // --- TRATAMENTO PARA NÃO DAR ERRO DE CHAVE ESTRANGEIRA ---
            
            // Se idBanca for 0, envia NULL pro banco
            if (tcc.getIdBanca() > 0) {
                stmt.setInt(7, tcc.getIdBanca());
            } else {
                stmt.setNull(7, java.sql.Types.INTEGER);
            }

            // Se idVersao for 0, envia NULL pro banco
            if (tcc.getIdVersao() > 0) {
                stmt.setInt(8, tcc.getIdVersao());
            } else {
                stmt.setNull(8, java.sql.Types.INTEGER);
            }
            // ---------------------------------------------------------

            stmt.executeUpdate();

        } catch (Exception e) {
            // Isso vai imprimir o erro no console se falhar
            e.printStackTrace(); 
            // Importante lançar o erro de volta para o Controller mostrar o Alerta
            throw new RuntimeException("Erro no DAO: " + e.getMessage());
        }
      }

      public TccModel buscarTccComOrientador(int idUsuario) {
            String sql = "SELECT t.*, u.nome AS nomeOrientador " +
                        "FROM tcc t INNER JOIN orientador o ON t.idOrientador = o.idUsuario " +
                        "INNER JOIN usuario u ON o.idUsuario = u.idUsuario " +
                        "WHERE t.idAluno = ?";
            TccModel tcc = null;

            try (Connection conn = ConexaoMySQL.getConnection();
                        PreparedStatement stmt = conn.prepareStatement(sql)) {

                  stmt.setInt(1, idUsuario);
                  var rs = stmt.executeQuery();
                  if (rs.next()) {
                        tcc = new TccModel();
                        tcc.setIdTcc(rs.getInt("idTCC"));
                        tcc.setTitulo(rs.getString("titulo"));
                        tcc.setEstado(rs.getString("estado"));
                        tcc.setResumo(rs.getString("resumo"));
                        tcc.setIdOrientador(rs.getInt("idOrientador"));
                        tcc.setNomeOrientador(rs.getString("nomeOrientador"));
                        tcc.setDataCadastro(rs.getDate("dataCadastro").toLocalDate());
                  }
            } catch (Exception e) {
                  e.printStackTrace();
            }

            return tcc;
      }

      public TccModel buscarTccPorUsuarioId(int idUsuario) {

            String sql = "SELECT * FROM tcc WHERE idAluno = ?";

            try (Connection conn = ConexaoMySQL.getConnection();
                        PreparedStatement stmt = conn.prepareStatement(sql)) {

                  stmt.setInt(1, idUsuario);
                  ResultSet rs = stmt.executeQuery();

                  if (rs.next()) {
                        TccModel tcc = new TccModel();
                        tcc.setIdTcc(rs.getInt("idTcc"));
                        tcc.setTitulo(rs.getString("titulo"));
                        tcc.setResumo(rs.getString("resumo"));
                        tcc.setEstado(rs.getString("estado"));
                        tcc.setDataCadastro(rs.getDate("dataCadastro").toLocalDate());
                        tcc.setIdAluno(rs.getInt("idAluno"));
                        tcc.setIdOrientador(rs.getInt("idOrientador"));

                        return tcc;
                  }

            } catch (Exception e) {
                  e.printStackTrace();
            }

            return null;
      }

      public List<TccModel> listar() {
            List<TccModel> lista = new ArrayList<>();
            String sql = "SELECT * FROM tcc";

            try (Connection conn = ConexaoMySQL.getConnection();
                        PreparedStatement stmt = conn.prepareStatement(sql);
                        ResultSet rs = stmt.executeQuery()) {

                  while (rs.next()) {
                        TccModel u = new TccModel();
                        u.setTitulo(rs.getString("titulo"));
                        u.setResumo(rs.getString("resumo"));
                        u.setEstado(rs.getString("estado"));
                        Date dataCadastro = rs.getDate("dataCadastro");
                        if (dataCadastro != null)
                              u.setDataCadastro(dataCadastro.toLocalDate());
                        u.setIdAluno(rs.getInt("idAluno"));
                        u.setIdOrientador(rs.getInt("idOrientador"));
                        lista.add(u);
                  }

            } catch (Exception e) {
                  e.printStackTrace();
            }

            return lista;
      }

      public List<TccModel> listarPorOrientador(int idOrientador) {
            List<TccModel> lista = new ArrayList<>();

            // --- AQUI ESTÁ A CORREÇÃO ---
            // Antes estava: ... ON t.idAluno = a.matricula (ERRADO)
            // Agora fica: ... ON t.idAluno = a.idUsuario (CERTO)

            String sql = "SELECT t.*, u.nome AS nomeAluno " +
                        "FROM tcc t " +
                        "INNER JOIN aluno a ON t.idAluno = a.idUsuario " +
                        "INNER JOIN usuario u ON a.idUsuario = u.idUsuario " +
                        "WHERE t.idOrientador = ?";

            try (Connection conn = ConexaoMySQL.getConnection();
                        PreparedStatement stmt = conn.prepareStatement(sql)) {

                  stmt.setInt(1, idOrientador);
                  ResultSet rs = stmt.executeQuery();

                  while (rs.next()) {
                        TccModel tcc = new TccModel();
                        tcc.setIdTcc(rs.getInt("idTcc"));
                        tcc.setTitulo(rs.getString("titulo"));
                        tcc.setResumo(rs.getString("resumo"));
                        tcc.setEstado(rs.getString("estado"));

                        // Recupera o nome do aluno para mostrar na tabela
                        tcc.setNomeAluno(rs.getString("nomeAluno"));

                        lista.add(tcc);
                  }

            } catch (Exception e) {
                  e.printStackTrace();
            }
            return lista;
      }

      public void atualizarEstado(TccModel tcc) {
            String sql = "UPDATE tcc SET estado = ? WHERE idTCC = ?";

            try (Connection conn = ConexaoMySQL.getConnection();
                        PreparedStatement stmt = conn.prepareStatement(sql)) {

                  stmt.setString(1, tcc.getEstado());
                  stmt.setInt(2, tcc.getIdTCC());

                  stmt.executeUpdate();
            } catch (SQLException e) {
                  e.printStackTrace();
            }
      }

      public void atualizarOrientador(TccModel tcc) {
            String sql = "UPDATE tcc SET idOrientador = ? WHERE idTCC = ?";

            try (Connection conn = ConexaoMySQL.getConnection();
                        PreparedStatement stmt = conn.prepareStatement(sql)) {

                  stmt.setInt(1, tcc.getIdOrientador());
                  stmt.setInt(2, tcc.getIdTCC());

                  stmt.executeUpdate();
            } catch (SQLException e) {
                  e.printStackTrace();
            }
      }

      public int contarOrientacoesAtivas() {
            String sql = "SELECT COUNT(*) FROM tcc WHERE estado = 'Ativa'";
            try (Connection conn = ConexaoMySQL.getConnection();
                  PreparedStatement stmt = conn.prepareStatement(sql);
                        ResultSet rs = stmt.executeQuery()) {

                  if (rs.next()) {
                        return rs.getInt(1);
                  }
            } catch (SQLException e) {
                  e.printStackTrace();
            }
            return 0;
      }

      // Conta pendentes de revisão
      public int contarPendentesRevisao() {
            String sql = "SELECT COUNT(*) FROM tcc WHERE estado = 'Em andamento'";
            try (Connection conn = ConexaoMySQL.getConnection();
                  PreparedStatement stmt = conn.prepareStatement(sql);
                        ResultSet rs = stmt.executeQuery()) {

                  if (rs.next()) {
                        return rs.getInt(1);
                  }
            } catch (SQLException e) {
                  e.printStackTrace();
            }
            return 0;
      }

      // Conta TCCs concluídos
      public int contarTCCsConcluidos() {
            String sql = "SELECT COUNT(*) FROM tcc WHERE estado = 'Concluido'";
            try (Connection conn = ConexaoMySQL.getConnection();
                  PreparedStatement stmt = conn.prepareStatement(sql);
                        ResultSet rs = stmt.executeQuery()) {

                  if (rs.next()) {
                        return rs.getInt(1);
                  }
            } catch (SQLException e) {
                  e.printStackTrace();
            }
            return 0;
      }

      public List<TccModel> listarTccComMaisDeUmAluno() {
            List<TccModel> lista = new ArrayList<>();
            String sql = "SELECT t.titulo, COUNT(a.idUsuario) AS qtdAlunos " +
                        "FROM TCC t " +
                        "JOIN Aluno a ON t.idAluno = a.idUsuario " +
                        "GROUP BY t.titulo " +
                        "HAVING COUNT(a.idUsuario) > 1";

            try (Connection conn = ConexaoMySQL.getConnection();
                        PreparedStatement stmt = conn.prepareStatement(sql);
                        ResultSet rs = stmt.executeQuery()) {

                  while (rs.next()) {
                        TccModel ta = new TccModel();
                        ta.setTitulo(rs.getString("titulo"));
                        ta.setIdAluno(rs.getInt("idAluno"));
                        lista.add(ta);
                  }

            } catch (SQLException e) {
                  e.printStackTrace();
            }
            return lista;
      }

      public boolean atualizar(TccModel tcc) {
            String sql = "UPDATE tcc SET titulo=?, resumo=? WHERE idTCC=?";

            try (Connection conn = ConexaoMySQL.getConnection();
                        PreparedStatement stmt = conn.prepareStatement(sql)) {

                  stmt.setString(1, tcc.getTitulo());
                  stmt.setString(2, tcc.getResumo());
                  stmt.setInt(3, tcc.getIdTCC());

                  return stmt.executeUpdate() > 0;

            } catch (Exception e) {
                  e.printStackTrace();
            }
            return false;
      }

      public void deletar(int idTCC) {

            try (Connection conn = ConexaoMySQL.getConnection();
                        PreparedStatement stmt1 = conn.prepareStatement(
                                    "DELETE FROM versaodocumento WHERE idTCC = ?")) {
                  stmt1.setInt(1, idTCC);
                  stmt1.executeUpdate();
            } catch (Exception e) {
                  e.printStackTrace();
            }

            String sql = "DELETE FROM tcc WHERE idTCC = ?";
            try (Connection conn = ConexaoMySQL.getConnection();
                        PreparedStatement stmt = conn.prepareStatement(sql)) {

                  stmt.setInt(1, idTCC);
                  stmt.executeUpdate();
                  System.out.println("TCC removido!");

            } catch (Exception e) {
                  e.printStackTrace();
            }
      }
}
