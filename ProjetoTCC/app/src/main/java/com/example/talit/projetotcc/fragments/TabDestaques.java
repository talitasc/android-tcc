package com.example.talit.projetotcc.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.example.talit.projetotcc.R;
import com.example.talit.projetotcc.activities.PaginaInicialEstabelecimentos;
import com.example.talit.projetotcc.activities.PaginalnicialConsumidor;
import com.example.talit.projetotcc.activities.ProdutosEstabelecimento;
import com.example.talit.projetotcc.activities.WelcomeScreen;
import com.example.talit.projetotcc.adapters.AdicionadosDestaques;
import com.example.talit.projetotcc.adapters.AvaliacoesAdapter;
import com.example.talit.projetotcc.adapters.CategoriasAdapter;
import com.example.talit.projetotcc.adapters.OfertasAdapter;
import com.example.talit.projetotcc.adapters.ProdutosAdapter;
import com.example.talit.projetotcc.connectionAPI.Categorias;
import com.example.talit.projetotcc.connectionAPI.ListarSupermercadosPorDescricao;
import com.example.talit.projetotcc.connectionAPI.LotePorCategoria;
import com.example.talit.projetotcc.connectionAPI.LotePorEstabelecimento;
import com.example.talit.projetotcc.connectionAPI.LotePorMarca;
import com.example.talit.projetotcc.connectionAPI.LotePorSubcategoria;
import com.example.talit.projetotcc.connectionAPI.Marcas;
import com.example.talit.projetotcc.connectionAPI.Subcategorias;
import com.example.talit.projetotcc.logicalView.Avaliacao;
import com.example.talit.projetotcc.logicalView.CategoriasProdutos;
import com.example.talit.projetotcc.logicalView.Estabelecimento;
import com.example.talit.projetotcc.logicalView.Produtos;
import com.example.talit.projetotcc.sqlight.DbConn;
import com.example.talit.projetotcc.volley.SliderUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static android.content.Context.MODE_PRIVATE;
import static com.example.talit.projetotcc.activities.SplashScreen.NOME_PREFERENCE;

/**
 * Created by talit on 05/06/2017.
 */

public class TabDestaques extends Fragment implements LotePorCategoria.Listener {

    public static Context context;
    public static Activity activity;
    public static RecyclerView recProdutos;
    public static CoordinatorLayout coordinatorLayout;
    private static ArrayList<Produtos> produtos = new ArrayList<Produtos>();
    private ArrayList<CategoriasProdutos> categProd = new ArrayList<CategoriasProdutos>();
    public static RelativeLayout no_produto;
    public static RelativeLayout no_Cadastrado;
    public static ProgressBar pbProdutos;
    public static String idCateg;
    public static String idSubCateg;
    public static String idMarca;
    public static AlertDialog dialogo;
    public static final String ID_ESTABELECIMENTO = "ID";
    public static String idEstab;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = container.getContext();
        activity = getActivity();
        View view = inflater.inflate(R.layout.fragment_destaques, container, false);
        recProdutos = (RecyclerView) view.findViewById(R.id.lv_prod);
        no_Cadastrado = (RelativeLayout) view.findViewById(R.id.rl_noCdastrado);
        no_produto = (RelativeLayout) view.findViewById(R.id.rl_no_produto);
        coordinatorLayout = (CoordinatorLayout)view.findViewById(R.id.destaque);
        pbProdutos = (ProgressBar) view.findViewById(R.id.pb_produtos);
        pbProdutos.setVisibility(View.INVISIBLE);

        StaggeredGridLayoutManager llm = new StaggeredGridLayoutManager(2, 1);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recProdutos.setLayoutManager(llm);

        SharedPreferences prefs = activity.getSharedPreferences(ID_ESTABELECIMENTO, MODE_PRIVATE);
        idEstab = prefs.getString("idEstab", null);

        if (idCateg != null) {
            LotePorCategoria connProd = new LotePorCategoria(null);
            connProd.execute(idCateg);
            Toast.makeText(activity, "click", Toast.LENGTH_SHORT).show();

        }else if(idMarca != null){
            LotePorMarca connMarca = new LotePorMarca(null);
            connMarca.execute(idMarca);
        }else if(idSubCateg != null){
            LotePorSubcategoria connSub = new LotePorSubcategoria(null);
            connSub.execute(idSubCateg);
        }else{
            LotePorEstabelecimento connEstab = new LotePorEstabelecimento(null);
            connEstab.execute(idEstab);
        }
        Log.i("IDeSTAB", idEstab);
        /*imFiltro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fltroSupermercado();
            }
        });*/

        return view;
    }
    @Override
    public void onLoaded(String result) {

        if (result.equalsIgnoreCase("Produtos")) {
            ProdutosAdapter produtosAdapter = new ProdutosAdapter(produtos, activity, context);
            recProdutos.setAdapter(produtosAdapter);
            produtosAdapter.notifyDataSetChanged();
        }
    }

}
