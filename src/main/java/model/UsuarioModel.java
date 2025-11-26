package model;

public class UsuarioModel {
      private int idUsuario;
      private String nome;
      private String email;
      private String tipo;
      private String senha;

      public UsuarioModel() {
      }

      public UsuarioModel(String nome, String email, String tipo, String senha) {
            this.nome = nome;
            this.email = email;
            this.tipo = tipo;
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

      public String getEmail() {
            return email;
      }

      public void setEmail(String email) {
            this.email = email;
      }

      public String getTipo() {
            return tipo;
      }

      public void setTipo(String tipo) {
            this.tipo = tipo;
      }

      public String getSenha() {
            return senha;
      }

      public void setSenha(String senha) {
            this.senha = senha;
      }
}
