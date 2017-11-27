package com.example.talit.projetotcc.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.talit.projetotcc.R;


public class DetalhesEstab extends Fragment {

    public static TextView nomeFantasia;
    public static TextView rua;
    public static TextView numero;
    public static TextView bairro;
    public static TextView cep;
    public static TextView cidade;
    public static TextView sigla;
    public static TextView ddd;
    public static TextView telefone;
    public static TextView email;
    public static String strNomeFantasia;
    public static String strRua;
    public static String strNumero;
    public static String strbairro;
    public static String strCep;
    public static String strCidade;
    public static String strSigla;
    public static String strDdd;
    public static String strTelefone;
    public static String strEmail;
    public static String strIdEstab;
    public static ProgressBar progressBar;

    public DetalhesEstab() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.card_view_apresentacao_estabelecimento, container, false);

        nomeFantasia = (TextView)view.findViewById(R.id.txt_nome_supermercado);
        rua = (TextView)view.findViewById(R.id.txt_rua);
        numero = (TextView)view.findViewById(R.id.txt_numero);
        bairro = (TextView)view.findViewById(R.id.txt_bairro);
        cep = (TextView)view.findViewById(R.id.txt_cep);
        cidade = (TextView)view.findViewById(R.id.txt_descricao);
        sigla = (TextView)view.findViewById(R.id.txt_sigla);
        ddd = (TextView)view.findViewById(R.id.txt_ddd);
        telefone = (TextView)view.findViewById(R.id.txt_telefone);
        email = (TextView)view.findViewById(R.id.txt_email);

        /*nomeFantasia.setText(strNomeFantasia);
        rua.setText(strRua);
        numero.setText(strNumero);
        bairro.setText(strbairro);
        cep.setText(strCep);
        cidade.setText(strCidade);
        sigla.setText(strSigla);
        ddd.setText(strDdd);
        telefone.setText(strTelefone);
        email.setText(strEmail);*/

        return view;
    }
    public static final void preencheDados(String strNomeFantasia,String strRua,String strNumero,String strbairro, String strCep,String strCidade,String strSigla,String strDdd, String strTelefone, String strEmail){
        nomeFantasia.setText(strNomeFantasia);
        rua.setText(strRua);
        numero.setText(strNumero);
        bairro.setText(strbairro);
        cep.setText(strCep);
        cidade.setText(strCidade);
        sigla.setText(strSigla);
        ddd.setText(strDdd);
        telefone.setText(strTelefone);
        email.setText(strEmail);
    }
}
