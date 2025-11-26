package model;

public class OrientadorModel {

      private String areaPesquisa;
      private String estado;
      private int idUsuario;

      public OrientadorModel() {
      }

      public OrientadorModel(String areaPesquisa, int idUsuario, String estado) {
            this.areaPesquisa = areaPesquisa;
            this.idUsuario = idUsuario;
            this.estado = estado;
      }

      public String getAreaPesquisa() {
            return areaPesquisa;
      }

      public void setAreaPesquisa(String areaPesquisa) {
            this.areaPesquisa = areaPesquisa;
      }

      public int getIdUsuario() {
            return idUsuario;
      }

      public void setIdUsuario(int idUsuario) {
            this.idUsuario = idUsuario;
      }

      public String getEstado() {
            return estado;
      }

      public void setEstado(String estado) {
            this.estado = estado;
      }

}
