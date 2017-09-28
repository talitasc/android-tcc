package com.example.talit.projetotcc.logicalView;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by talit on 23/04/2017.
 */

public class Estabelecimento implements Cloneable {
    private int id;
    private String nomeFantasia;
    private int tipoEstabelecimento;
    private String rua;
    private int numero;
    private String bairro;
    private String cep;
    private String cidade;
    private String estadoSigla;
    private String ddd;
    private String telefone;
    private String email;

    public Estabelecimento(int id, String nomeFantasia, int tipoEstabelecimento, String rua, int numero, String bairro, String cep, String cidade, String estadoSigla, String ddd, String telefone, String email) {
        this.id = id;
        this.nomeFantasia = nomeFantasia;
        this.tipoEstabelecimento = tipoEstabelecimento;
        this.rua = rua;
        this.numero = numero;
        this.bairro = bairro;
        this.cep = cep;
        this.cidade = cidade;
        this.estadoSigla = estadoSigla;
        this.ddd = ddd;
        this.telefone = telefone;
        this.email = email;
    }
    public Estabelecimento(Estabelecimento est){
        try {

            this.id = est.id;
            this.nomeFantasia = est.nomeFantasia;
            this.tipoEstabelecimento = est.tipoEstabelecimento;
            this.rua= est.rua;
            this.numero = est.numero;
            this.bairro = est.bairro;
            this.cep= est.cep;
            this.cidade = est.cidade;
            this.estadoSigla = est.estadoSigla;
            this.ddd = est.ddd;
            this.telefone = est.telefone;
            this.email = est.telefone;
            this.email = est.email;

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    protected Estabelecimento(Parcel in) {
        id = in.readInt();
        nomeFantasia = in.readString();
        tipoEstabelecimento = in.readInt();
        rua = in.readString();
        numero = in.readInt();
        bairro = in.readString();
        cep = in.readString();
        cidade = in.readString();
        estadoSigla = in.readString();
        ddd = in.readString();
        telefone = in.readString();
        email = in.readString();
    }

    public Object clone(){

        Object clone = null;

        try{
            clone = new Estabelecimento(this);
        }catch (Exception e){
            e.printStackTrace();
        }
        return clone;
    }

    public Estabelecimento(int id, String nomeFantasia, String cidade) {
        this.id = id;
        this.nomeFantasia = nomeFantasia;
        this.cidade = cidade;
    }

    public int getId() {
        return id;
    }

    public int getTipoEstabelecimento() {
        return tipoEstabelecimento;
    }

    public int getNumero() {
        return numero;
    }

    public String getRua() {
        return rua;
    }

    public String getBairro() {
        return bairro;
    }

    public String getCep() {
        return cep;
    }

    public String getCidade() {
        return cidade;
    }

    public String getEstadoSigla() {
        return estadoSigla;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getEmail() {
        return email;
    }

    public String getDdd() {
        return ddd;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTipoEstabelecimento(int tipoEstabelecimento) {
        this.tipoEstabelecimento = tipoEstabelecimento;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public void setEstadoSigla(String estadoSigla) {
        this.estadoSigla = estadoSigla;
    }

    public void setDdd(String ddd) {
        this.ddd = ddd;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getIdEstabelecimento() {
        return id;
    }

    public void setIdEstabelecimento(int id) {
        this.id = id;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

}
