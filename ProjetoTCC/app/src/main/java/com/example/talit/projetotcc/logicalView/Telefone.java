package com.example.talit.projetotcc.logicalView;

/**
 * Created by talit on 19/03/2017.
 */

public class Telefone implements Cloneable {

    private int idTelefone;
    private String idTf;
    private String ddd;
    private String numeroTelefone;

    public Telefone(String idTf, String ddd, String numeroTelefone) {

        this.idTf = idTf;
        this.ddd = ddd;
        this.numeroTelefone = numeroTelefone;
    }
    public Telefone(int idTelefone, String idTf, String ddd, String numeroTelefone) {

        this.idTelefone = idTelefone;
        this.idTf = idTf;
        this.ddd = ddd;
        this.numeroTelefone = numeroTelefone;
    }
    public Telefone(Telefone tel){

        try{
            this.idTelefone = tel.idTelefone;
            this.idTf = tel.idTf;
            this.ddd = tel.ddd;
            this.numeroTelefone = tel.numeroTelefone;

        }catch(Exception e){
            e.printStackTrace();
        }

    }
    public Object clone(){

        Object clone = null;

        try{
            clone = new Telefone(this);
        }catch (Exception e){
            e.printStackTrace();
        }
        return clone;
    }
    public int getIdTelefone() {
        return idTelefone;
    }

    public void setIdTelefone(int idTelefone) {
        this.idTelefone = idTelefone;
    }

    public String getIdTf() {
        return idTf;
    }

    public void setIdTf(String idTf) {
        this.idTf = idTf;
    }

    public String getDdd() {
        return ddd;
    }

    public void setDdd(String ddd) {
        this.ddd = ddd;
    }

    public String getNumeroTelefone() {
        return numeroTelefone;
    }

    public void setNumeroTelefone(String numeroTelefone) {
        this.numeroTelefone = numeroTelefone;
    }
}
