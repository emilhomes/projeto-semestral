package model;

import java.time.LocalDate;


public class TccModel {

      private int idTCC;
      private String titulo;
      private String resumo;
      private String estado;
      private LocalDate dataCadastro;
      private int idAluno;
      private int idOrientador;
      private String nomeOrientador;
      private String nomeAluno;

      public TccModel() {
      }

      public TccModel(String titulo, String resumo, String estado, LocalDate dataCadastro, int idAluno, int idOrientador) {
            this.titulo = titulo;
            this.resumo = resumo;
            this.estado = estado;
            this.dataCadastro = dataCadastro;
            this.idAluno = idAluno;
            this.idOrientador = idOrientador;
      }

      public int getIdTCC() {
            return idTCC;
      }

      public void setIdTcc(int idTCC) {
            this.idTCC = idTCC;
      }

      public String getTitulo() {
            return titulo;
      }

      public void setTitulo(String titulo) {
            this.titulo = titulo;
      }

      public String getResumo() {
            return resumo;
      }

      public void setResumo(String resumo) {
            this.resumo = resumo;
      }

      public String getEstado() {
            return estado;
      }

      public void setEstado(String estado) {
            this.estado = estado;
      }

      public LocalDate getDataCadastro() {
            return dataCadastro;
      }

      public void setDataCadastro(LocalDate dataCadastro) {
            this.dataCadastro = dataCadastro;
      }

      public int getIdAluno() {
            return idAluno;
      }

      public void setIdAluno(int idAluno) {
            this.idAluno = idAluno;
      }

      public int getIdOrientador() {
            return idOrientador;
      }

      public void setIdOrientador(int idOrientador) {
            this.idOrientador = idOrientador;
      }
      public String getNomeOrientador() {
            return nomeOrientador;
      }
      public void setNomeOrientador(String nomeOrientador) {
            this.nomeOrientador = nomeOrientador;
      }

      public void setNomeAluno(String nomeAluno) {
            this.nomeAluno = nomeAluno;
      }

      public String getNomeAluno() {
            return nomeAluno;
      }

}
