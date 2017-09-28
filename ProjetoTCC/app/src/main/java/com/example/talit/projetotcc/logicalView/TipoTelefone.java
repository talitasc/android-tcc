package com.example.talit.projetotcc.logicalView;

/**
 * Created by talit on 13/03/2017.
 */

public class TipoTelefone implements Cloneable{

    private int idTipo;
    private String tipo;
    private String mascara;


    public TipoTelefone(int idTipo, String tipo, String mascara) {
        this.idTipo = idTipo;
        this.tipo = tipo;
        this.mascara = mascara;
    }
    public TipoTelefone(TipoTelefone tf){
        try{

            this.idTipo = tf.idTipo;
            this.tipo = tf.tipo;
            this.mascara = tf.mascara;

        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public Object clone(){

        Object clone = null;

        try{
            clone = new TipoTelefone(this);
        }catch (Exception e){
            e.printStackTrace();
        }
        return clone;
    }

    public int getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(int idTipo) {
        this.idTipo = idTipo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getMascara() {
        return mascara;
    }

    public void setMascara(String mascara) {
        this.mascara = mascara;
    }
}

