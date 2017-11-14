package com.example.talit.projetotcc.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.example.talit.projetotcc.R;
import com.example.talit.projetotcc.adapters.BrindesAdaper;
import com.example.talit.projetotcc.connectionAPI.BrindesEstabelecimentos;
import com.example.talit.projetotcc.connectionAPI.LotePorEstabelecimento;
import com.example.talit.projetotcc.logicalView.Produtos;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by talit on 12/11/2017.
 */

public class BrindesEstabelecimento extends Fragment implements BrindesEstabelecimentos.Listener{

    public static Activity activity;
    public static Context context;
    public static RecyclerView recProdutos;
    public static ProgressBar pbProdutos;
    public static final String ID_ESTABELECIMENTO = "ID";
    public static String idEstab;
    public static RelativeLayout no_produto;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = container.getContext();
        activity = getActivity();
        View view = inflater.inflate(R.layout.fragment_brindes_estabelecimentos, container, false);
        pbProdutos = (ProgressBar) view.findViewById(R.id.pb_produtos);
        recProdutos = (RecyclerView) view.findViewById(R.id.lv_prod);
        no_produto = (RelativeLayout) view.findViewById(R.id.rl_no_produto);
        pbProdutos.setVisibility(View.INVISIBLE);

        StaggeredGridLayoutManager llm = new StaggeredGridLayoutManager(2, 1);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recProdutos.setLayoutManager(llm);

        SharedPreferences prefs = activity.getSharedPreferences(ID_ESTABELECIMENTO, MODE_PRIVATE);
        idEstab = prefs.getString("idEstab", null);

        BrindesEstabelecimentos connEstab = new BrindesEstabelecimentos(null);
        connEstab.execute(idEstab);

        return view;
    }

    @Override
    public void onLoaded(List<Produtos> prods) {

        BrindesAdaper brindesAdaper = new BrindesAdaper(prods, BrindesEstabelecimento.activity, BrindesEstabelecimento.context);
        recProdutos.setAdapter(brindesAdaper);
        brindesAdaper.notifyDataSetChanged();
    }
}
