package com.example.talit.projetotcc.logicalView;

/**
 * Created by talit on 08/10/2017.
 */

public class Endereco  {

    private String rua;
    private String numero;
    private String bairro;
    private String complemento;
    private String cep;
    private String cidade_descricao;
    private String estado_sigla;
    private String endereco_id;


    public Endereco(String rua, String numero, String bairro, String complemento, String cep,String cidade_descricao, String estado_sigla ) {
        this.rua = rua;
        this.numero = numero;
        this.bairro = bairro;
        this.complemento = complemento;
        this.cep = cep;
        this.cidade_descricao = cidade_descricao;
        this.estado_sigla = estado_sigla;
    }

    public Endereco(String rua, String numero, String bairro, String complemento, String cep, String cidade_descricao, String estado_sigla, String endereco_id) {
        this.rua = rua;
        this.numero = numero;
        this.bairro = bairro;
        this.complemento = complemento;
        this.cep = cep;
        this.cidade_descricao = cidade_descricao;
        this.estado_sigla = estado_sigla;
        this.endereco_id = endereco_id;
    }

    public String getEndereco_id() {
        return endereco_id;
    }

    public void setEndereco_id(String endereco_id) {
        this.endereco_id = endereco_id;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getCidade_descricao() {
        return cidade_descricao;
    }

    public void setCidade_descricao(String cidade_descricao) {
        this.cidade_descricao = cidade_descricao;
    }

    public String getEstado_sigla() {
        return estado_sigla;
    }

    public void setEstado_sigla(String estado_sigla) {
        this.estado_sigla = estado_sigla;
    }
}
