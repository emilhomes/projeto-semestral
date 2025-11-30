package model;

import java.time.LocalDate;

public class PrazoInstitucionalModel {

    private int idPrazo;
    private String nome;
    private String descricao;
    private LocalDate dataInicio;
    private LocalDate dataFinal;
    private int idCordenador;

    public PrazoInstitucionalModel() {
    }


    public String getDescricaoBanco() {
        String n = (this.nome != null) ? this.nome : "";
        String d = (this.descricao != null) ? this.descricao : "";
        return n + " - " + d;
    }

    public void setDescricaoBanco(String textoDoBanco) {
        if (textoDoBanco != null && textoDoBanco.contains(" - ")) {
            String[] partes = textoDoBanco.split(" - ", 2);
            this.nome = partes[0];
            this.descricao = partes[1];
        } else {
            this.nome = "Sem Nome";
            this.descricao = textoDoBanco;
        }
    }

    public int getIdPrazo() {
        return idPrazo;
    }

    public void setIdPrazo(int idPrazo) {
        this.idPrazo = idPrazo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(LocalDate dataFinal) {
        this.dataFinal = dataFinal;
    }

    public int getIdCordenador() {
        return idCordenador;
    }

    public void setIdCordenador(int idCordenador) {
        this.idCordenador = idCordenador;
    }
}