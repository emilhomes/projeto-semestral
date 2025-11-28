package dao;

import model.BancaModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import conexao.ConexaoMySQL;

public class BancaDAO {

      public void inserir(BancaModel banca) {
            String sql = "INSERT INTO banca(dataDefesa, menbros, idOrientador) VALUES (?, ?, ? )";

            try (Connection conn = ConexaoMySQL.getConnection();
                        PreparedStatement stmt = conn.prepareStatement(sql)) {

                  stmt.setDate(1, java.sql.Date.valueOf(banca.getDataDefesa()));
                  stmt.setString(2, banca.getMenbros());
                  stmt.setInt(3, banca.getIdOrientador());
                  stmt.executeUpdate();

            } catch (Exception e) {
                  e.printStackTrace();
            }
      }

      public List<BancaModel> listar() {
            List<BancaModel> lista = new ArrayList<>();
            String sql = "SELECT * FROM banca";

            try (Connection conn = ConexaoMySQL.getConnection();
                        PreparedStatement stmt = conn.prepareStatement(sql);
                        ResultSet rs = stmt.executeQuery()) {

                  while (rs.next()) {
                        BancaModel u = new BancaModel();
                  
                        u.setMenbros(rs.getString("menbros"));
                        u.setIdOrientador(rs.getInt("idOrientador"));
                         Date dataDefesa = rs.getDate("dataDefesa");
                         if (dataDefesa != null)
                               u.setDataDefesa(dataDefesa.toLocalDate());
                        lista.add(u);
                  }

            } catch (Exception e) {
                  e.printStackTrace();
            }

            return lista;
      }

      public void atualizar(BancaModel banca) {
            String sql = "UPDATE banca SET dataDefesa = ?, menbros = ?, idOrientador = ? WHERE idBanca = ?";

            try (Connection conn = ConexaoMySQL.getConnection();
                        PreparedStatement stmt = conn.prepareStatement(sql)) {

                  stmt.setString(1, banca.getMenbros());
                  stmt.setInt(2, banca.getIdOrientador());
                  stmt.setInt(3, banca.getIdBanca());
                  stmt.setDate(4, java.sql.Date.valueOf(banca.getDataDefesa()));
                  stmt.executeUpdate();

                  System.out.println("Banca atualizado!");

            } catch (Exception e) {
                  e.printStackTrace();
            }
      }

      public void deletar(int idBanca) {
            String sql = "DELETE FROM banca idBanca = ?";

            try (Connection conn = ConexaoMySQL.getConnection();
                        PreparedStatement stmt = conn.prepareStatement(sql)) {

                  stmt.setInt(1, idBanca);
                  stmt.executeUpdate();
                  System.out.println("Banca removido!");

            } catch (Exception e) {
                  e.printStackTrace();
            }
      }
}
