 package com.example.talit.projetotcc.utils;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;

import com.example.talit.projetotcc.mascaras.MascaraTelefone;

 /**
 * Created by talit on 12/03/2017.
 */

public class Validacoes {

    public static  String[] verifica;

     public static final boolean validaEmail(String email){

        if(email.contains("@")){

            verifica = email.split("@");
            return (verifica.length > 1);
        }
        return false;
    }
    public static final boolean validaSenha(String senha){

        return senha.length() > 4;
    }
    public static final void requestFocus(View focusView) { focusView.requestFocus(); }

    public static final boolean validaCPF(String cpf){
        return cpf.matches("[0-9]{3}\\.?[0-9]{3}\\.?[0-9]{3}\\-?[0-9]{2}");
    }
    public static final boolean validaTelefone(String tel){
        return tel.matches("[^0-9]*");
    }

    public static final boolean isNumeric(String str)
    {
        return str.matches("^\\d{2}.\\d{3}.\\d{3}/\\d{4}-\\d{2}$");
    }
    public static boolean isValidFone(String texto)
    {
        texto = MascaraTelefone.unmask(texto);

        if (texto.length() < 8 || texto.length() > 9)
        {
            return false;
        }

        return true;
    }
     public static boolean verifyConnection(Context context)
     {
         ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
         NetworkInfo netInfo = cm.getActiveNetworkInfo();
         if (netInfo != null && netInfo.isConnected()) {
             return true;
         } else {
             return false;
         }
     }
}
