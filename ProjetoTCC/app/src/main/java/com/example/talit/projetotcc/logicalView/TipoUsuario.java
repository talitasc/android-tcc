package com.example.talit.projetotcc.logicalView;

/**
 * Created by talit on 26/03/2017.
 */

public class TipoUsuario implements Cloneable {
    private int idTipoUsuario;
    private String descricao;


    public TipoUsuario(int idTipoUsuario, String descricao) {
        this.idTipoUsuario = idTipoUsuario;
        this.descricao = descricao;
    }
    public TipoUsuario(TipoUsuario copia){
        try{
            this.idTipoUsuario = copia.idTipoUsuario;
            this.descricao = copia.descricao;

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public Object clone(){

        Object clone = null;

        try{
            clone = new TipoUsuario(this);
        }catch (Exception e){
            e.printStackTrace();
        }
        return clone;
    }
    public int getIdTipoUsuario() {
        return idTipoUsuario;
    }

    public void setIdTipoUsuario(int idTipoUsuario) {
        this.idTipoUsuario = idTipoUsuario;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
