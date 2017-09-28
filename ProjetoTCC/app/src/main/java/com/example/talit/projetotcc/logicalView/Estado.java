package com.example.talit.projetotcc.logicalView;

/**
 * Created by talit on 23/05/2017.
 */

public class Estado implements Cloneable{
    private int idEstado;
    private String siglaEstado;
    private String descricaoEstado;

    public Estado(int idEstado, String siglaEstado, String descricaoEstado) {
        this.idEstado = idEstado;
        this.siglaEstado = siglaEstado;
        this.descricaoEstado = descricaoEstado;
    }

    public Estado(Estado es){

        try{

            this.idEstado = es.idEstado;
            this.siglaEstado = es.siglaEstado;
            this.descricaoEstado = es.descricaoEstado;


        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public Object clone(){

        Object clone = null;

        try{
            clone = new Estado(this);
        }catch (Exception e){
            e.printStackTrace();
        }
        return clone;
    }

    public int getIdEstado() {
        return idEstado;
    }

    public String getSiglaEstado() {
        return siglaEstado;
    }

    public String getDescricaoEstado() {
        return descricaoEstado;
    }

    public void setIdEstado(int idEstado) {
        this.idEstado = idEstado;
    }

    public void setSiglaEstado(String siglaEstado) {
        this.siglaEstado = siglaEstado;
    }

    public void setDescricaoEstado(String descricaoEstado) {
        this.descricaoEstado = descricaoEstado;
    }

}
