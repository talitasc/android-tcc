package com.example.talit.projetotcc.logicalView;

import java.util.Date;

/**
 * Created by talit on 13/03/2017.
 */

public class Usuario implements Cloneable {

    private int idUsuario;
    private TipoUsuario idTipoUsuario;
    private StatusUsuario idStatus;
    private String usuario;
    private String senha;
    private Email idEmail;
    private Date dataCadastro;

    public Usuario(int idUsuario,TipoUsuario idTipoUsuario,StatusUsuario idStatus,String usuario, String senha, Email idEmail, Date dataCadastro){
        this.idUsuario = idUsuario;
        this.idTipoUsuario = (TipoUsuario)idTipoUsuario.clone();
        this.idStatus = (StatusUsuario)idStatus.clone();
        this.usuario = usuario;
        this.senha = senha;
        this.idEmail = (Email)idEmail.clone();
        this.dataCadastro = dataCadastro;

    }
    public Usuario(Usuario copia){
        try {
            this.idUsuario = copia.idUsuario;
            this.idTipoUsuario =(TipoUsuario) copia.idTipoUsuario.clone();
            this.idStatus = (StatusUsuario) copia.idStatus.clone();
            this.usuario = copia.usuario;
            this.senha = copia.senha;
            this.idEmail = (Email) copia.idEmail.clone();
            this.dataCadastro = copia.dataCadastro;

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public Object clone(){

        Object clone = null;

        try{
            clone = new Usuario(this);
        }catch (Exception e){
            e.printStackTrace();
        }
        return clone;
    }
    public void setIdUsuario(int idUsuario){
        this.idUsuario = idUsuario;
    }
    public int getIdUsuario(){
        return this.idUsuario;
    }
    public void setUsuario(String usuario){
        this.usuario = usuario;
    }
    public String getUsuario(){
        return this.usuario;
    }
    public void setSenha(String senha){
        this.senha = senha;
    }
    public String getSenha(){
        return this.senha;
    }
    public TipoUsuario getIdTipoUsuario() {
        return idTipoUsuario;
    }
    public void setIdTipoUsuario(TipoUsuario idTipoUsuario) {
        this.idTipoUsuario = idTipoUsuario;
    }

    public StatusUsuario getIdTipoStatus() {
        return idStatus;
    }

    public void setIdTipoStatus(StatusUsuario idStatus) {
        this.idStatus= idStatus;
    }

    public StatusUsuario getIdStatus() {
        return idStatus;
    }

    public void setIdStatus(StatusUsuario idStatus) {
        this.idStatus = idStatus;
    }

    public Email getIdEmail() {
        return idEmail;
    }

    public void setIdEmail(Email idEmail) {
        this.idEmail  = (Email) idEmail.clone();
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }
}
