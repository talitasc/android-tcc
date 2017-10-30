package com.example.talit.projetotcc.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import com.example.talit.projetotcc.adapters.CategoriasAdapter;
import com.example.talit.projetotcc.adapters.OfertasAdapter;
import com.example.talit.projetotcc.adapters.ProdutosAdapter;
import com.example.talit.projetotcc.connectionAPI.Categorias;
import com.example.talit.projetotcc.connectionAPI.ListarSupermercadosPorDescricao;
import com.example.talit.projetotcc.connectionAPI.LotePorCategoria;
import com.example.talit.projetotcc.connectionAPI.Marcas;
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

public class TabDestaques extends Fragment implements Categorias.Listener, LotePorCategoria.Listener {

    public static Context context;
    public static Activity activity;
    public static RecyclerView rec;
    public static RecyclerView recProdutos;
    public static RecyclerView recMarca;
    private static ArrayList<Produtos> produtos = new ArrayList<Produtos>();
    private ArrayList<CategoriasProdutos> categProd = new ArrayList<CategoriasProdutos>();
    public static RelativeLayout no_produto;
    public static RelativeLayout no_categoria;
    public static RelativeLayout no_Cadastrado;
    public static RelativeLayout no_marcas;
    public static RelativeLayout no_sub;
    public static ProgressBar pb;
    public static ProgressBar pbProdutos;
    public static ProgressBar pbMarcas;
    public static String idCateg;
    private ImageButton imFiltro;

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
        no_Cadastrado = (RelativeLayout) view.findViewById(R.id.rl_noCdastrado);
        no_produto = (RelativeLayout) view.findViewById(R.id.rl_no_produto);
        no_categoria = (RelativeLayout) view.findViewById(R.id.rl_no_catgeorias);
        pbProdutos = (ProgressBar) view.findViewById(R.id.pb_produtos);
        pb = (ProgressBar) view.findViewById(R.id.pb_categorias);
        imFiltro = (ImageButton)view.findViewById(R.id.imgfiltroEstab);

        pb.setVisibility(View.INVISIBLE);
        pbProdutos.setVisibility(View.INVISIBLE);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        rec.setLayoutManager(layoutManager);

        Categorias conn = new Categorias(null);
        conn.execute();

        StaggeredGridLayoutManager llm = new StaggeredGridLayoutManager(2, 1);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recProdutos.setLayoutManager(llm);

        if (idCateg != null) {
            LotePorCategoria connProd = new LotePorCategoria(null);
            connProd.execute(idCateg);
            Toast.makeText(activity, "click", Toast.LENGTH_SHORT).show();
        }
        imFiltro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fltroSupermercado();
            }
        });

        return view;
    }
    public void fltroSupermercado() {

        LayoutInflater inflater = activity.getLayoutInflater();
        final View alertLayout = inflater.inflate(R.layout.custom_alerta_dialog_subcategorias, null);
        Button cancelar = (Button) alertLayout.findViewById(R.id.cancelar);
        recMarca = (RecyclerView)alertLayout.findViewById(R.id.rec_marcas);
        pbMarcas = (ProgressBar) alertLayout.findViewById(R.id.pb_marcas);
        no_sub = (RelativeLayout)alertLayout.findViewById(R.id.no_sub);
        no_produto = (RelativeLayout)alertLayout.findViewById(R.id.no_marcas);

        AlertDialog.Builder alerta = new AlertDialog.Builder(getActivity());
        alerta.setView(alertLayout);
        alerta.setCancelable(false);
        final AlertDialog dialogo = alerta.create();
        dialogo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogo.show();
        Marcas connMarca= new Marcas(null);
        connMarca.execute();

       /* seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                txtRaio.setText(String.format(String.valueOf(progress)));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogo.dismiss();
                houveBusca = true;
                listas.removeAllViews();
                raio = txtRaio.getText().toString();
                ListaSupermercadoPoRaio connRaio = new ListaSupermercadoPoRaio(null);
                connRaio.execute(String.format("%s", latitude), String.format("%s",longitude),raio);


            }
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogo.dismiss();
            }

        });*/
    }
    @Override
    public void onLoaded(String result) {

        if (result.equalsIgnoreCase("Categoria")) {
            CategoriasAdapter categoriasAdapter = new CategoriasAdapter(activity, categProd);
            rec.setAdapter(categoriasAdapter);
            categoriasAdapter.notifyDataSetChanged();
        }
        if (result.equalsIgnoreCase("Produtos")) {
            ProdutosAdapter produtosAdapter = new ProdutosAdapter(produtos, activity, context);
            recProdutos.setAdapter(produtosAdapter);
            produtosAdapter.notifyDataSetChanged();
        }
    }

}
