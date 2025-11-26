package model;


public class ComentarioModel {

      private int idComentario;
      private String usuario;
      private String conteudo;

      public ComentarioModel() {
      }

      public ComentarioModel(String usuario, String conteudo) {
            this.usuario = usuario;
            this.conteudo = conteudo;

      }

      public int getIdComentario() {
            return idComentario;
      }

      public void setIdComentario(int idComentario) {
            this.idComentario = idComentario;
      }

      public String getUsuario() {
            return usuario;
      }

      public void setUsuario(String usuario) {
            this.usuario = usuario;
      }

      public String getConteudo() {
            return conteudo;
      }

      public void setConteudo(String conteudo) {
            this.conteudo = conteudo;
      }

}
