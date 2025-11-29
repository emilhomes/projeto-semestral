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
      private String tipo; // Vai receber "PESSOAL", "BANCA" ou "INSTITUCIONAL"

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

      // --- GETTER E SETTER DO NOVO CAMPO ---
      public String getTipo() {
            return tipo;
      }

      public void setTipo(String tipo) {
            this.tipo = tipo;
      }
      // -------------------------------------

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
            // Recebe "Titulo - Descricao" do banco e separa
            if (textoDoBanco != null && textoDoBanco.contains(" - ")) {
                  String[] partes = textoDoBanco.split(" - ", 2); // Divide em 2 partes no primeiro " - "
                  this.titulo = partes[0];
                  this.descricao = partes[1];
            } else {
                  // Se não tiver o separador, assume que é tudo descrição
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