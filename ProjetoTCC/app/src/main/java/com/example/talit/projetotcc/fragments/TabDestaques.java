package com.example.talit.projetotcc.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.android.volley.RequestQueue;
import com.example.talit.projetotcc.R;
import com.example.talit.projetotcc.activities.PaginalnicialConsumidor;
import com.example.talit.projetotcc.activities.ProdutosEstabelecimento;
import com.example.talit.projetotcc.activities.WelcomeScreen;
import com.example.talit.projetotcc.adapters.AdicionadosDestaques;
import com.example.talit.projetotcc.adapters.CategoriasAdapter;
import com.example.talit.projetotcc.adapters.OfertasAdapter;
import com.example.talit.projetotcc.adapters.ProdutosAdapter;
import com.example.talit.projetotcc.connectionAPI.Categorias;
import com.example.talit.projetotcc.connectionAPI.ListarSupermercadosPorDescricao;
import com.example.talit.projetotcc.connectionAPI.LotePorCategoria;
import com.example.talit.projetotcc.logicalView.CategoriasProdutos;
import com.example.talit.projetotcc.logicalView.Produtos;
import com.example.talit.projetotcc.sqlight.DbConn;
import com.example.talit.projetotcc.volley.SliderUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by talit on 05/06/2017.
 */

public class TabDestaques extends Fragment implements Categorias.Listener{

    public static Context context;
    public static Activity activity;
    public static RecyclerView rec;
    public static RecyclerView recProdutos;
    private ArrayList<Produtos> produtos = new ArrayList<Produtos>();
    public static RelativeLayout no_Cadastrado;
    public static ProgressBar pb;
    public static ViewPager viewPager;
    public LinearLayout linearLayout;
    private int dotsCount;
    private DbConn dbconn;
    private ImageView[] dots;
    RequestQueue rq;
    List<SliderUtils> sliderImg;
    String reques_url = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = container.getContext();
        activity = getActivity();
        View view = inflater.inflate(R.layout.fragment_destaques, container, false);
        rec = (RecyclerView) view.findViewById(R.id.rec_categ);
        recProdutos = (RecyclerView) view.findViewById(R.id.lv_prod);
        no_Cadastrado = (RelativeLayout)view.findViewById(R.id.rl_noCdastrado);
        pb = (ProgressBar)view.findViewById(R.id.pb_categorias);
        pb.setVisibility(View.INVISIBLE);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        rec.setLayoutManager(layoutManager);

        Categorias conn = new Categorias(null);
        conn.execute();

        StaggeredGridLayoutManager llm = new StaggeredGridLayoutManager(2, 1);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recProdutos.setLayoutManager(llm);

        LotePorCategoria connProd = new LotePorCategoria(null);
        connProd.execute("5");

        return view;
    }

    @Override
    public void onLoaded(List<CategoriasProdutos> categProd) {
        CategoriasAdapter categoriasAdapter  = new CategoriasAdapter(TabDestaques.activity,categProd);
        rec.setAdapter(categoriasAdapter);
    }
}
