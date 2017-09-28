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
import android.widget.RelativeLayout;

import com.android.volley.RequestQueue;
import com.example.talit.projetotcc.R;
import com.example.talit.projetotcc.activities.PaginalnicialConsumidor;
import com.example.talit.projetotcc.activities.ProdutosEstabelecimento;
import com.example.talit.projetotcc.activities.WelcomeScreen;
import com.example.talit.projetotcc.adapters.AdicionadosDestaques;
import com.example.talit.projetotcc.adapters.OfertasAdapter;
import com.example.talit.projetotcc.adapters.ProdutosAdapter;
import com.example.talit.projetotcc.connectionAPI.ListarSupermercadosPorDescricao;
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

public class TabDestaques extends Fragment {
    public static Context context;
    public static Activity activity;
    private RecyclerView rec;
    private ArrayList<Produtos> produtos = new ArrayList<Produtos>();
    public static RelativeLayout no_Cadastrado;
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
        rec = (RecyclerView) view.findViewById(R.id.lv_destaques);
        no_Cadastrado = (RelativeLayout)view.findViewById(R.id.rl_noCdastrado);
        viewPager = (ViewPager) view.findViewById(R.id.view_pager);
        OfertasAdapter ofertasAdapter = new OfertasAdapter(context);
        viewPager.setAdapter(ofertasAdapter);

        dotsCount = viewPager.getCurrentItem();
        dots = new ImageView[dotsCount];

        for (int i = 0; i>dotsCount; i++){
            dots[i] = new ImageView(context);
            dots[i].setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.bt_botoes_aux));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(8, 0, 8, 0);
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        rec.setLayoutManager(layoutManager);
        //dbconn = new DbConn(getActivity());

        produtos.add(new Produtos(R.drawable.logo_super, 1, "Bolacha Recheada", "passatempo", 00.50, "Passatempo sabor Morango com 143g Nabisco. Para valores nutricionais estão informados no verso do produto.", "Validade:20/06/2017","1"));
        produtos.add(new Produtos(R.drawable.logo_super, 2, "Biscoito Salgado", "Pit Stop", 1.75, "Biscoito agua e sal Pit stop com 9 Unidades.Para valores nutricionais estão informados no verso do produto.", "Validade:20/06/2017","2"));
        produtos.add(new Produtos(R.drawable.logo_super, 3, "Creme de Amendoin", "Paçoquita", 2.00, "Paçoquita cremosa 180G.Para valores nutricionais estão informados no verso do produto", "Validade:20/06/2017","3"));
        produtos.add(new Produtos(R.drawable.logo_super, 4, "Bolacha Recheada", "passatempo", 00.50, "Passatempo sabor Morango com 143g Nabisco. Para valores nutricionais estão informados no verso do produto.", "Validade:20/06/2017","4"));
        produtos.add(new Produtos(R.drawable.logo_super, 5, "Biscoito Salgado", "Pit Stop", 1.75, "Biscoito agua e sal Pit stop com 9 Unidades.Para valores nutricionais estão informados no verso do produto.", "Validade:20/06/2017","5"));
        produtos.add(new Produtos(R.drawable.logo_super, 6, "Creme de Amendoin", "Paçoquita", 2.00, "Paçoquita cremosa 180G.Para valores nutricionais estão informados no verso do produto", "Validade:20/06/2017","6"));

        rec.setAdapter(new AdicionadosDestaques(produtos, getActivity()));
        //rec.setAdapter(new ProdutosAdapter(produtos, getActivity()));*/

        return view;
    }

}
