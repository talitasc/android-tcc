package com.example.talit.projetotcc.logicalView;

/**
 * Created by talit on 24/05/2017.
 */

public class Cidade implements Cloneable{

    private int idCidade;
    private int idEstado;
    private String descricaoCidade;

    public Cidade(int idCidade, int idEstado, String descricaoCidade) {

        this.idCidade = idCidade;
        this.idEstado = idEstado;
        this.descricaoCidade = descricaoCidade;
    }

    public Cidade(Cidade cid){

        try{
            this.idCidade = cid.idCidade;
            this.idEstado = cid.idEstado;
            this.descricaoCidade = cid.descricaoCidade;

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public Object clone(){

        Object clone = null;

        try{
            clone = new Cidade(this);
        }catch (Exception e){
            e.printStackTrace();
        }
        return clone;
    }

    public int getIdCidade() {
        return idCidade;
    }

    public String getDescricaoCidade() {
        return descricaoCidade;
    }

    public void setIdCidade(int idCidade) {
        this.idCidade = idCidade;
    }

    public void setDescricaoCidade(String descricaoCidade) {
        this.descricaoCidade = descricaoCidade;
    }

    public void setIdEstado(int idEstado) {
        this.idEstado = idEstado;
    }
    public int getIdEstado() {

        return idEstado;
    }

}
