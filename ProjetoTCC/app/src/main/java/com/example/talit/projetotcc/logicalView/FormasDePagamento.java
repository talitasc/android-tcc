package com.example.talit.projetotcc.logicalView;

/**
 * Created by talit on 02/12/2017.
 */

public class FormasDePagamento {

    private int idPagamento;
    private String descricao;

    public FormasDePagamento(int idPagamento, String descricao) {
        this.idPagamento = idPagamento;
        this.descricao = descricao;
    }

    public int getIdPagamento() {
        return idPagamento;
    }

    public void setIdPagamento(int idPagamento) {
        this.idPagamento = idPagamento;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
