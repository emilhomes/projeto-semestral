package model;

public class UsuarioModel {
      private int idUsuario;
      private String nome;
      private String emailInstitucional;
      private String tipoUsuario;
      private String senha;

      public UsuarioModel() {
      }

      public UsuarioModel(String nome, String emailInstitucional, String tipoUsuario, String senha) {
            this.nome = nome;
            this.emailInstitucional = emailInstitucional;
            this.tipoUsuario = tipoUsuario;
            this.senha = senha;
      }

      public int getIdUsuario() {
            return idUsuario;
      }

      public void setIdUsuario(int idUsuario) {
            this.idUsuario = idUsuario;
      }

      public String getNome() {
            return nome;
      }

      public void setNome(String nome) {
            this.nome = nome;
      }

      public String getEmailInstitucional() {
            return emailInstitucional;
      }

      public void setEmailInstitucional(String emailInstitucional) {
            this.emailInstitucional = emailInstitucional;
      }

      public String getTipoUsuario() {
            return tipoUsuario;
      }

      public void setTipoUsuario(String tipoUsuario) {
            this.tipoUsuario = tipoUsuario;
      }

      public String getSenha() {
            return senha;
      }

      public void setSenha(String senha) {
            this.senha = senha;
      }
}
