package com.example.talit.projetotcc.logicalView;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by talit on 23/04/2017.
 */

public class Estabelecimento implements Cloneable {

    private int id;
    private String cnpj;
    private String razao_social;
    private String nome_fantasia;
    private String inscricao_estadual;
    private String inscricao_municipal;
    private String estab_vendedor;
    private String tipo_estab_desc;
    private String rua;
    private String numero;
    private String bairro;
    private String complemento;
    private String cep;
    private String tpTel;
    private String dd;
    private String telefone;
    private String email;
    private String cidade;
    private String estado_sigla;

    public Estabelecimento(){
        this.id= 0;
    }
    public Estabelecimento(int id, String cnpj, String razao_social, String nome_fantasia, String inscricao_estadual, String inscricao_municipal, String estab_vendedor, String tipo_estab_desc, String email) {

        this.id = id;
        this.cnpj = cnpj;
        this.razao_social = razao_social;
        this.nome_fantasia = nome_fantasia;
        this.inscricao_estadual = inscricao_estadual;
        this.inscricao_municipal = inscricao_municipal;
        this.estab_vendedor = estab_vendedor;
        this.tipo_estab_desc = tipo_estab_desc;
        this.email = email;
    }

    public Estabelecimento(int id, String cnpj, String razao_social, String nome_fantasia, String inscricao_estadual, String inscricao_municipal, String estab_vendedor, String tipo_estab_desc, String email,String rua, String numero, String bairro, String complemento, String cep,String cidade,String sigla,String tpTel, String dd, String telefone) {
        this.id = id;
        this.cnpj = cnpj;
        this.razao_social = razao_social;
        this.nome_fantasia = nome_fantasia;
        this.inscricao_estadual = inscricao_estadual;
        this.inscricao_municipal = inscricao_municipal;
        this.estab_vendedor = estab_vendedor;
        this.tipo_estab_desc = tipo_estab_desc;
        this.email = email;
        this.rua = rua;
        this.numero = numero;
        this.bairro = bairro;
        this.complemento = complemento;
        this.cep = cep;
        this.cidade = cidade;
        this.estado_sigla = sigla;
        this.tpTel = tpTel;
        this.dd = dd;
        this.telefone = telefone;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getRazao_social() {
        return razao_social;
    }

    public void setRazao_social(String razao_social) {
        this.razao_social = razao_social;
    }

    public String getNome_fantasia() {
        return nome_fantasia;
    }

    public void setNome_fantasia(String nome_fantasia) {
        this.nome_fantasia = nome_fantasia;
    }

    public String getInscricao_estadual() {
        return inscricao_estadual;
    }

    public void setInscricao_estadual(String inscricao_estadual) {
        this.inscricao_estadual = inscricao_estadual;
    }

    public String getInscricao_municipal() {
        return inscricao_municipal;
    }

    public void setInscricao_municipal(String inscricao_municipal) {
        this.inscricao_municipal = inscricao_municipal;
    }

    public String getEstab_vendedor() {
        return estab_vendedor;
    }

    public void setEstab_vendedor(String estab_vendedor) {
        this.estab_vendedor = estab_vendedor;
    }

    public String getTipo_estab_desc() {
        return tipo_estab_desc;
    }

    public void setTipo_estab_desc(String tipo_estab_desc) {
        this.tipo_estab_desc = tipo_estab_desc;
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

    public String getDd() {
        return dd;
    }

    public void setDd(String dd) {
        this.dd = dd;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getTpTel() {
        return tpTel;
    }

    public void setTpTel(String tpTel) {
        this.tpTel = tpTel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado_sigla() {
        return estado_sigla;
    }

    public void setEstado_sigla(String estado_sigla) {
        this.estado_sigla = estado_sigla;
    }
}

