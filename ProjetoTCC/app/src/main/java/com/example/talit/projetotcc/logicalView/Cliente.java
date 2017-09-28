package com.example.talit.projetotcc.logicalView;

/**
 * Created by talit on 13/03/2017.
 */

public class Cliente implements Cloneable {

    private Usuario idUsuario;
    private TipoUsuario idTpUsuario;
    private String nome;
    private String sobrenome;


    public Cliente(Usuario idUsuario, TipoUsuario idTpUsuario, String nome, String sobrenome) {
        this.idUsuario = (Usuario) idUsuario.clone();
        this.idTpUsuario = (TipoUsuario) idTpUsuario.clone();
        this.nome = nome;
        this.sobrenome = sobrenome;

    }
    public Cliente(Cliente cl){

        try{

            this.idUsuario = (Usuario)cl.idUsuario.clone();
            this.idTpUsuario = (TipoUsuario)cl.idTpUsuario.clone();
            this.nome = cl.nome;
            this.sobrenome = cl.sobrenome;

        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public Object clone(){

        Object clone = null;

        try{
            clone = new Cliente(this);
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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

}
