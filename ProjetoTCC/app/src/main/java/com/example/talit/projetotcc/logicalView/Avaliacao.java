package com.example.talit.projetotcc.logicalView;

/**
 * Created by talit on 10/11/2017.
 */

public class Avaliacao {

    private String nome_avaliador;
    private int nota_avaliacao;
    private String descricao_avaliacao;
    private String data_avaliacao;

    public Avaliacao(String nome_avaliador, int nota_avaliacao, String descricao_avaliacao, String data_avaliacao) {
        this.nome_avaliador = nome_avaliador;
        this.nota_avaliacao = nota_avaliacao;
        this.descricao_avaliacao = descricao_avaliacao;
        this.data_avaliacao = data_avaliacao;
    }

    public String getNome_avaliador() {
        return nome_avaliador;
    }

    public void setNome_avaliador(String nome_avaliador) {
        this.nome_avaliador = nome_avaliador;
    }

    public int getNota_avaliacao() {
        return nota_avaliacao;
    }

    public void setNota_avaliacao(int nota_avaliacao) {
        this.nota_avaliacao = nota_avaliacao;
    }

    public String getDescricao_avaliacao() {
        return descricao_avaliacao;
    }

    public void setDescricao_avaliacao(String descricao_avaliacao) {
        this.descricao_avaliacao = descricao_avaliacao;
    }

    public String getData_avaliacao() {
        return data_avaliacao;
    }

    public void setData_avaliacao(String data_avaliacao) {
        this.data_avaliacao = data_avaliacao;
    }
}
