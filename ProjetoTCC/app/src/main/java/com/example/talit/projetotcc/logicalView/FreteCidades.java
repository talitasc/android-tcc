package com.example.talit.projetotcc.logicalView;

/**
 * Created by talit on 21/11/2017.
 */

public class FreteCidades {

    private int frete_id;
    private String estabelecimento_id;
    private String cidade_descricao;
    private String frete_valor;

    public FreteCidades(String frete_valor){
        this.frete_valor = frete_valor;
    }

    public FreteCidades(int frete_id, String estabelecimento_id, String cidade_descricao, String frete_valor) {
        this.frete_id = frete_id;
        this.estabelecimento_id = estabelecimento_id;
        this.cidade_descricao = cidade_descricao;
        this.frete_valor = frete_valor;
    }

    public int getFrete_id() {
        return frete_id;
    }

    public void setFrete_id(int frete_id) {
        this.frete_id = frete_id;
    }

    public String getEstabelecimento_id() {
        return estabelecimento_id;
    }

    public void setEstabelecimento_id(String estabelecimento_id) {
        this.estabelecimento_id = estabelecimento_id;
    }

    public String getCidade_descricao() {
        return cidade_descricao;
    }

    public void setCidade_descricao(String cidade_descricao) {
        this.cidade_descricao = cidade_descricao;
    }

    public String getFrete_valor() {
        return frete_valor;
    }

    public void setFrete_valor(String frete_valor) {
        this.frete_valor = frete_valor;
    }
}
