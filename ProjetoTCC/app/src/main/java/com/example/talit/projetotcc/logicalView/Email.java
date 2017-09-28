package com.example.talit.projetotcc.logicalView;

/**
 * Created by talit on 13/03/2017.
 */

public class Email implements Cloneable{

    private int idEmail;
    private String descrição;

    public Email(int idEmail, String descrição){
        this.idEmail = idEmail;
        this.descrição = descrição;

    }
    public Email(Email copia){

       try {

           this.idEmail = copia.idEmail;
           this.descrição = copia.descrição;

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

    public String getDescrição() {
        return descrição;
    }

    public void setDescrição(String descrição) {
        this.descrição = descrição;
    }

}
