package com.example.talit.projetotcc.mascaras;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * Created by talit on 18/03/2017.
 */

public abstract class MascaraCpf {

    public static String unmask(String str){
        return str.replaceAll("[.]","").replaceAll("[-]","")
                .replaceAll("[/]","").replaceAll("[(]","")
                .replaceAll("[)]","");
    }

    public static TextWatcher insert(final String mask,final EditText edtTxt){

        return new TextWatcher() {
            boolean foiAlterado;
            String antiga = "";

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                String str = MascaraCpf.unmask(s.toString());
                String mascara = "";

                if(foiAlterado){
                    antiga = str;
                    foiAlterado = false;
                    return;
                }
                int i = 0;
                for(char m: mask.toCharArray()){

                    if(m != '#' && str.length() > antiga.length()){
                        mascara += m;
                        continue;
                    }
                    try{
                        mascara += str.charAt(i);
                    }catch (Exception e){
                        break;
                    }
                    i++;
                    foiAlterado = true;
                    edtTxt.setText(mascara);
                    edtTxt.setSelection(mascara.length());
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
    }
}
