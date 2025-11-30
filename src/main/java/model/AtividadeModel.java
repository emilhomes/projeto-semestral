package model;

import java.time.LocalDate;

public class AtividadeModel {

      private int idAtividade;
      private String titulo;
      private String descricao;
      private String descricaoCompleta;
      private LocalDate dataInicio;
      private LocalDate dataFim;
      private String estado;
      private String tipo; 

      public AtividadeModel() {
      }

      public AtividadeModel(String titulo, String descricao, String descricaoCompleta, LocalDate dataInicio, LocalDate dataFim, String estado) {
            this.titulo = titulo;
            this.descricao = descricao;
            this.descricaoCompleta = descricaoCompleta;
            this.dataInicio = dataInicio;
            this.dataFim = dataFim;
            this.estado = estado;
      }

      
      public String getTipo() {
            return tipo;
      }

      public void setTipo(String tipo) {
            this.tipo = tipo;
      }
  

      public void setTitulo(String titulo) {
            this.titulo = titulo;
      }

      public String getTitulo() {
            return titulo;
      }

      public int getIdAtividade() {
            return idAtividade;
      }

      public void setIdAtividade(int idAtividade) {
            this.idAtividade = idAtividade;
      }

      public String getDescricao() {
            return descricao;
      }

      public void setDescricao(String descricao) {
            this.descricao = descricao;
      }

      public String getDescricaoBanco() {
            String t = (this.titulo != null) ? this.titulo : "";
            String d = (this.descricao != null) ? this.descricao : "";
        
            return t + " - " + d;
      }

      public void setDescricaoBanco(String textoDoBanco) {
            
            if (textoDoBanco != null && textoDoBanco.contains(" - ")) {
                  String[] partes = textoDoBanco.split(" - ", 2); 
                  this.descricao = partes[1];
            } else {
                  
                  this.titulo = "Sem Título"; 
                  this.descricao = textoDoBanco;
            }
            this.descricaoCompleta = textoDoBanco;
    }

      public LocalDate getDataInicio() {
            return dataInicio;
      }

      public void setDataInicio(LocalDate dataInicio) {
            this.dataInicio = dataInicio;
      }

      public LocalDate getDataFim() {
            return dataFim;
      }

      public void setDataFim(LocalDate dataFim) {
            this.dataFim = dataFim;
      }

      public String getEstado() {
            return estado;
      }

      public void setEstado(String estado) {
            this.estado = estado;
      }
}