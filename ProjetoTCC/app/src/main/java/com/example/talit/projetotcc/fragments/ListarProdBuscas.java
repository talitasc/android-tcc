package com.example.talit.projetotcc.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.example.talit.projetotcc.R;
import com.example.talit.projetotcc.activities.SearchViewEstabelecimento;
import com.example.talit.projetotcc.adapters.BuscaProdAdapter;
import com.example.talit.projetotcc.connectionAPI.BuscaFullTextProdutos;
import com.example.talit.projetotcc.logicalView.Produtos;

import java.util.List;

/**
 * Created by talit on 12/11/2017.
 */

public class ListarProdBuscas extends android.support.v4.app.Fragment implements BuscaFullTextProdutos.Listener{

    public static RecyclerView recProds;
    public static Activity activity;
    public static Context context;
    public static ProgressBar pbBuscas;
    public static RelativeLayout relativeNoEstabs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = container.getContext();
        activity = getActivity();
        View view = inflater.inflate(R.layout.fragment_buscas_prod, container, false);
        recProds = (RecyclerView)view.findViewById(R.id.lv_prods);
        pbBuscas = (ProgressBar)view.findViewById(R.id.pb_buscas);
        relativeNoEstabs= (RelativeLayout)view.findViewById(R.id.rl_nolist);
        pbBuscas.setVisibility(View.INVISIBLE);

        recProds.setHasFixedSize(true);
        StaggeredGridLayoutManager llm = new StaggeredGridLayoutManager(1, 1);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recProds.setLayoutManager(llm);

        return view;
    }

    @Override
    public void onLoaded(List<Produtos> prods) {
        BuscaProdAdapter buscaProdAdapter  = new BuscaProdAdapter(prods, getActivity(), getContext());
        recProds.setAdapter(buscaProdAdapter);
        buscaProdAdapter.notifyDataSetChanged();
    }
}
