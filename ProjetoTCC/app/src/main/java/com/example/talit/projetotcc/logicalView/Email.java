package com.example.talit.projetotcc.logicalView;

/**
 * Created by talit on 13/03/2017.
 */

public class Email implements Cloneable{

    private int idEmail;
    private String descricao;

    public Email(int idEmail, String descricao){
        this.idEmail = idEmail;
        this.descricao = descricao;

    }
    public Email(Email copia){

       try {

           this.idEmail = copia.idEmail;
           this.descricao = copia.descricao;

       }catch (Exception e){
            e.printStackTrace();
       }
    }
    public Object clone(){

        Object clone = null;

        try{
            clone = new Email(this);
        }catch (Exception e){
            e.printStackTrace();
        }
        return clone;
    }
    public int getIdEmail() {
        return idEmail;
    }

    public void setIdEmail(int idEmail) {
        this.idEmail = idEmail;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

}
