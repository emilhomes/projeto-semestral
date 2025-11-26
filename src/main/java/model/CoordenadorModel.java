package model;

public class CoordenadorModel {
      private String curso;
      private int idUsuario;

      public CoordenadorModel() {
      }

      public CoordenadorModel( String curso, int idUsuario) {

            this.curso = curso;
            this.idUsuario = idUsuario;
      }

      public String getCurso() {
            return curso;
      }
      public void setCurso(String curso) {
            this.curso = curso;
      }

      public int getIdUsuario() {
            return idUsuario;
      }

      public void setIdUsuario(int idUsuario) {
            this.idUsuario = idUsuario;
      }

}