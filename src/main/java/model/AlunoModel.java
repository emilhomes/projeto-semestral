package model;

public class AlunoModel {

      private int matricula;
      private String tcc;
      private String curso;
      private int idUsuario;

      public AlunoModel() {
      }

      public AlunoModel(int matricula, String tcc, String curso, int idUsuario) {
            this.matricula = matricula;
            this.tcc = tcc;
            this.curso = curso;
            this.idUsuario = idUsuario;
      }

      public int getMatricula() {
            return matricula;
      }

      public void setMatricula(int matricula) {
            this.matricula = matricula;
      }

      public String getTcc() {
            return tcc;
      }

      public void setTcc(String tcc) {
            this.tcc = tcc;
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