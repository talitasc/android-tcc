package com.example.talit.projetotcc.logicalView;

/**
 * Created by talit on 26/03/2017.
 */

public class StatusUsuario implements Cloneable{

    private int idStatus;
    private String descricao;


    public StatusUsuario(int idStatus, String descricao) {
        this.idStatus = idStatus;
        this.descricao = descricao;
    }
    public StatusUsuario(StatusUsuario su){
        try{
            this.idStatus = su.idStatus;
            this.descricao = su.descricao;

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public Object clone(){

        Object clone = null;

        try{
            clone = new StatusUsuario(this);
        }catch (Exception e){
            e.printStackTrace();
        }
        return clone;
    }
    public int getIdStatus() {
        return idStatus;
    }

    public void setIdStatus(int idStatus) {
        this.idStatus = idStatus;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

}
