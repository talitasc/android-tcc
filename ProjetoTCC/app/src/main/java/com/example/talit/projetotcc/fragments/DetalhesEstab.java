package com.example.talit.projetotcc.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.talit.projetotcc.R;


public class DetalhesEstab extends Fragment {

    private TextView nomeFantasia;
    private TextView rua;
    private TextView numero;
    private TextView bairro;
    private TextView cep;
    private TextView cidade;
    private TextView sigla;
    private TextView ddd;
    private TextView telefone;
    private TextView email;
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

        return view;
    }
}
