package com.example.talit.projetotcc.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.talit.projetotcc.R;
import com.example.talit.projetotcc.adapters.CategoriasAdapter;
import com.example.talit.projetotcc.adapters.ProdutosAdapter;
import com.example.talit.projetotcc.connectionAPI.Categorias;
import com.example.talit.projetotcc.logicalView.CategoriasProdutos;
import com.example.talit.projetotcc.logicalView.Estabelecimento;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by talit on 05/06/2017.
 */

public class TabCategorias extends Fragment implements Categorias.Listener{

    public static Context context;
    public static Activity activity;
    public static ListView listView;
    public static ProgressBar pb;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = container.getContext();
        activity = getActivity();
        View view = inflater.inflate(R.layout.fragment_categorias, container, false);
        listView = (ListView) view.findViewById(R.id.lv_categoria);
        pb = (ProgressBar)view.findViewById(R.id.pb_categorias);
        pb.setVisibility(View.INVISIBLE);
        Categorias conn = new Categorias(null);
        conn.execute();
        return view;
    }

    @Override
    public void onLoaded(List<CategoriasProdutos> categProd) {

        //CategoriasAdapter categoriasAdapter = new CategoriasAdapter(TabCategorias.activity, TabCategorias.context, categProd);
        //this.listView.setAdapter(categoriasAdapter);
        //categoriasAdapter.notifyDataSetChanged();
    }
}
