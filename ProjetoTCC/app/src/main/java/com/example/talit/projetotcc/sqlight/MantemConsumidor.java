package com.example.talit.projetotcc.sqlight;

import com.example.talit.projetotcc.logicalView.Usuario;

/**
 * Created by talit on 27/03/2017.
 */

public class MantemConsumidor implements Cloneable {

    private int idCons;
    private String usuario;
    private String senha;
    private int status;
    private int tpAcesso;

    public MantemConsumidor(int idCons,String usuario, String senha, int status, int tpAcesso) {
        this.idCons = idCons;
        this.usuario = usuario;
        this.senha = senha;
        this.status = status;
        this.tpAcesso = tpAcesso;
    }
    public MantemConsumidor( MantemConsumidor mc){

        try{

            this.idCons = mc.idCons;
            this.usuario = mc.usuario;
            this.senha = mc.senha;
            this.status = mc.status;
            this.tpAcesso = mc.tpAcesso;

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public Object clone(){

        Object clone = null;

        try{
            clone = new MantemConsumidor(this);
        }catch (Exception e){
            e.printStackTrace();
        }
        return clone;
    }
    public int getIdCons() {
        return idCons;
    }

    public void setIdCons(int idCons) {
        this.idCons = idCons;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getTpAcesso() {
        return tpAcesso;
    }

    public void setTpAcesso(int tpAcesso) {
        this.tpAcesso = tpAcesso;
    }
}
