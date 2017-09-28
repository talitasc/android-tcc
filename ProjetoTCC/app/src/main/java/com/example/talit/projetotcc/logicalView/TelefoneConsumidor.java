package com.example.talit.projetotcc.logicalView;

/**
 * Created by talit on 26/03/2017.
 */

public class TelefoneConsumidor implements Cloneable{

    private Usuario idUsuario;
    private TipoUsuario idTpUsuario;
    private Telefone idTelefone;


    public TelefoneConsumidor(Usuario idUsuario, TipoUsuario idTpUsuario, Telefone idTelefone) {
        this.idUsuario = (Usuario) idUsuario.clone();
        this.idTpUsuario = (TipoUsuario) idTpUsuario.clone();
        this.idTelefone = (Telefone)idTelefone.clone();
    }
    public TelefoneConsumidor(TelefoneConsumidor tlfc){
        try{
            this.idUsuario = (Usuario) tlfc.idUsuario.clone();
            this.idTpUsuario = (TipoUsuario)tlfc.idTpUsuario.clone();
            this.idTelefone = (Telefone)tlfc.idTelefone.clone();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public Object clone(){

        Object clone = null;

        try{
            clone = new TelefoneConsumidor(this);
        }catch (Exception e){
            e.printStackTrace();
        }
        return clone;
    }

    public Usuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Usuario idUsuario) {
        this.idUsuario = idUsuario;
    }

    public TipoUsuario getIdTpUsuario() {
        return idTpUsuario;
    }

    public void setIdTpUsuario(TipoUsuario idTpUsuario) {
        this.idTpUsuario = idTpUsuario;
    }

    public Telefone getIdTelefone() {
        return idTelefone;
    }

    public void setIdTelefone(Telefone idTelefone) {
        this.idTelefone = idTelefone;
    }
}
