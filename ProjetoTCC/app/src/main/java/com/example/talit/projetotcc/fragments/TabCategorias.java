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

import com.example.talit.projetotcc.R;
import com.example.talit.projetotcc.adapters.CategoriasAdapter;
import com.example.talit.projetotcc.adapters.ProdutosAdapter;
import com.example.talit.projetotcc.logicalView.CategoriasProdutos;

import java.util.ArrayList;

/**
 * Created by talit on 05/06/2017.
 */

public class TabCategorias extends Fragment {

    public static Context context;
    public static Activity activity;
    public static ListView listView;
    private ArrayList<CategoriasProdutos> categorias =  new ArrayList<CategoriasProdutos>();

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


        categorias.add(new CategoriasProdutos(1,"Todos os produtos"));
        categorias.add(new CategoriasProdutos(2,"Bebidas"));
        categorias.add(new CategoriasProdutos(3,"Carnes"));
        categorias.add(new CategoriasProdutos(4,"Frios"));
        categorias.add(new CategoriasProdutos(5,"Higiene"));
        categorias.add(new CategoriasProdutos(6,"Frutas"));
        categorias.add(new CategoriasProdutos(7,"Legumes"));
        categorias.add(new CategoriasProdutos(8,"Limpeza"));
        categorias.add(new CategoriasProdutos(9,"Padaria"));
        categorias.add(new CategoriasProdutos(10,"Outros"));

        CategoriasAdapter categs = new CategoriasAdapter(getActivity(),getContext(),categorias);
        listView.setAdapter(categs);
        return view;
    }
}
