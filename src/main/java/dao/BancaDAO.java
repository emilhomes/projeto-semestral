package dao;

import model.BancaModel;
import conexao.ConexaoMySQL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BancaDAO {

    public void inserir(BancaModel banca) {

        String sql = "INSERT INTO banca(dataDefesa, menbros) VALUES (?, ?)";

        try (Connection conn = ConexaoMySQL.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            if (banca.getDataDefesa() != null) {
                stmt.setDate(1, java.sql.Date.valueOf(banca.getDataDefesa()));
            } else {
                stmt.setNull(1, java.sql.Types.DATE);
            }

            stmt.setString(2, banca.getMenbros());

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

                u.setIdBanca(rs.getInt("idBanca"));

                u.setMenbros(rs.getString("menbros"));

                Date dataDefesa = rs.getDate("dataDefesa");
                if (dataDefesa != null) {
                    u.setDataDefesa(dataDefesa.toLocalDate());
                }

                lista.add(u);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    public void atualizar(BancaModel banca) {

        String sql = "UPDATE banca SET dataDefesa = ?, menbros = ? WHERE idBanca = ?";

        try (Connection conn = ConexaoMySQL.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            if (banca.getDataDefesa() != null) {
                stmt.setDate(1, java.sql.Date.valueOf(banca.getDataDefesa()));
            } else {
                stmt.setNull(1, java.sql.Types.DATE);
            }

            stmt.setString(2, banca.getMenbros());

            stmt.setInt(3, banca.getIdBanca());

            stmt.executeUpdate();
            System.out.println("Banca atualizada!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deletar(int idBanca) {

        String sql = "DELETE FROM banca WHERE idBanca = ?";

        try (Connection conn = ConexaoMySQL.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idBanca);
            stmt.executeUpdate();
            System.out.println("Banca removida!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<BancaModel> listarPorOrientador(int idOrientador) {
        List<BancaModel> lista = new ArrayList<>();
        
        // O SQL que funcionou no seu Workbench:
        String sql = "SELECT b.* FROM banca b " +
                     "INNER JOIN tcc t ON t.idBanca = b.idBanca " +
                     "WHERE t.idOrientador = ?";

        try (Connection conn = ConexaoMySQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idOrientador);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                BancaModel u = new BancaModel();
                u.setIdBanca(rs.getInt("idBanca"));
                u.setMenbros(rs.getString("menbros"));
                
                Date dataDefesa = rs.getDate("dataDefesa");
                if (dataDefesa != null) {
                    u.setDataDefesa(dataDefesa.toLocalDate());
                }
                
                lista.add(u);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
}