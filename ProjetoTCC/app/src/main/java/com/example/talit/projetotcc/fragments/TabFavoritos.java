package com.example.talit.projetotcc.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.talit.projetotcc.R;

/**
 * Created by talit on 22/04/2017.
 */

public class TabFavoritos extends Fragment {
    public static RecyclerView listas;
    public static Context context;
    public static Activity activity;
    public static RelativeLayout no_list;
    public static int idEstado;
    public static int idCidade;
    protected SwipeRefreshLayout refresh;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_tab_favoritos, container, false);
        listas = (RecyclerView)view.findViewById(R.id.lv_supermercado);
        no_list = (RelativeLayout)view.findViewById(R.id.rl_nolist);

        /*refresh = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);*/
        listas.setHasFixedSize(true);
        StaggeredGridLayoutManager llm = new StaggeredGridLayoutManager(1, 1);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        listas.setLayoutManager(llm);
        return view;
    }
}
