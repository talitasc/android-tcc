package com.example.talit.projetotcc.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.talit.projetotcc.R;
import com.example.talit.projetotcc.activities.SearchViewPaginaInicial;
import com.example.talit.projetotcc.adapters.BuscaAdapter;
import com.example.talit.projetotcc.adapters.FulltextEstabelecimentoAdapter;
import com.example.talit.projetotcc.connectionAPI.BuscaFullText;
import com.example.talit.projetotcc.logicalView.Busca;
import com.example.talit.projetotcc.logicalView.EstabelecimentoFullText;
import com.example.talit.projetotcc.sqlight.DbConn;

import java.util.List;

/**
 * Created by talit on 12/11/2017.
 */

public class Buscas extends Fragment {

    private TextView txtSugestao1;
    private TextView txtSugestao2;
    private TextView txtSugestao3;
    private TextView txtSugestao4;
    private TextView txtSugestao5;
    private TextView txtSugestao6;
    private TextView txtSugestao7;
    private TextView txtSugestao8;
    private TextView txtSugestao9;
    private TextView txtSugestao10;
    private TextView txtSugestao11;
    private TextView txtSugestao12;
    private TextView txtSugestao13;
    private TextView semSugestao;
    private DbConn dbconn;
    public static ListView listview;
    public static Activity activity;
    public static Context context;
    public static RelativeLayout relativeBuscas;
    public  static Button btnExcluir;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_buscas, container, false);
        context = container.getContext();
        activity = getActivity();
        txtSugestao1 = (TextView) view.findViewById(R.id.sugestao1);
        txtSugestao2 = (TextView) view.findViewById(R.id.sugestao2);
        txtSugestao3 = (TextView) view.findViewById(R.id.sugestao3);
        txtSugestao4 = (TextView) view.findViewById(R.id.sugestao4);
        txtSugestao5 = (TextView) view.findViewById(R.id.sugestao5);
        txtSugestao6 = (TextView) view.findViewById(R.id.sugestao6);
        txtSugestao7 = (TextView) view.findViewById(R.id.sugestao7);
        txtSugestao8 = (TextView) view.findViewById(R.id.sugestao8);
        txtSugestao9 = (TextView) view.findViewById(R.id.sugestao9);
        txtSugestao10 = (TextView) view.findViewById(R.id.sugestao10);
        txtSugestao11 = (TextView) view.findViewById(R.id.sugestao11);
        txtSugestao12 = (TextView) view.findViewById(R.id.sugestao12);
        txtSugestao13 = (TextView) view.findViewById(R.id.sugestao13);
        semSugestao = (TextView) view.findViewById(R.id.sem_sugestão);
        listview = (ListView) view.findViewById(R.id.list_suggestios);
        relativeBuscas = (RelativeLayout)view.findViewById(R.id.id_subestoes_busca);
        btnExcluir = (Button)view.findViewById(R.id.brn_exclui_hist);

        txtSugestao1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchViewPaginaInicial.searchView.setQuery(getResources().getString(R.string.txt_sugestoes_1), true);
            }
        });
        txtSugestao2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchViewPaginaInicial.searchView.setQuery(getResources().getString(R.string.txt_sugestoes_2), true);
            }
        });
        txtSugestao3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchViewPaginaInicial.searchView.setQuery(getResources().getString(R.string.txt_sugestoes_3), true);
            }
        });
        txtSugestao4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchViewPaginaInicial.searchView.setQuery(getResources().getString(R.string.txt_sugestoes_4), true);
            }
        });
        txtSugestao5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchViewPaginaInicial.searchView.setQuery(getResources().getString(R.string.txt_sugestoes_5), true);
            }
        });
        txtSugestao6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchViewPaginaInicial.searchView.setQuery(getResources().getString(R.string.txt_sugestoes_6), true);
            }
        });
        txtSugestao7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchViewPaginaInicial.searchView.setQuery(getResources().getString(R.string.txt_sugestoes_7), true);
            }
        });
        txtSugestao8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchViewPaginaInicial.searchView.setQuery(getResources().getString(R.string.txt_sugestoes_8), true);
            }
        });
        txtSugestao9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchViewPaginaInicial.searchView.setQuery(getResources().getString(R.string.txt_sugestoes_9), true);
            }
        });
        txtSugestao10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchViewPaginaInicial.searchView.setQuery(getResources().getString(R.string.txt_sugestoes_10), true);
            }
        });
        txtSugestao11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchViewPaginaInicial.searchView.setQuery(getResources().getString(R.string.txt_sugestoes_11), true);
            }
        });
        txtSugestao12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchViewPaginaInicial.searchView.setQuery(getResources().getString(R.string.txt_sugestoes_12), true);
            }
        });
        txtSugestao13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchViewPaginaInicial.searchView.setQuery(getResources().getString(R.string.txt_sugestoes_13), true);
            }
        });

        dbconn = new DbConn(getActivity());

        if (dbconn.selectHistorico() != null) {
            BuscaAdapter buscaAdapter = new BuscaAdapter(getActivity(), context, dbconn.selectHistorico());
            listview.setAdapter(buscaAdapter);
            listview.deferNotifyDataSetChanged();

        }
        if (listview.getCount() == 0) {
            listview.setVisibility(View.INVISIBLE);
            semSugestao.setVisibility(View.VISIBLE);
        }
        btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbconn.deleteHistorico();
                startActivity(new Intent(getActivity(),SearchViewPaginaInicial.class));
                getActivity().finish();
            }
        });
        return view;
    }
}
