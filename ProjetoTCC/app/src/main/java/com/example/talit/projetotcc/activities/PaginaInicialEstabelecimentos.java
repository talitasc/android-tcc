package com.example.talit.projetotcc.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.talit.projetotcc.R;
import com.example.talit.projetotcc.adapters.CategoriasAdapter;
import com.example.talit.projetotcc.connectionAPI.Categorias;
import com.example.talit.projetotcc.connectionAPI.DeleteCarrinho;
import com.example.talit.projetotcc.connectionAPI.Marcas;
import com.example.talit.projetotcc.connectionAPI.Subcategorias;
import com.example.talit.projetotcc.fragments.DetalhesEstab;
import com.example.talit.projetotcc.fragments.TabCategorias;
import com.example.talit.projetotcc.fragments.TabDestaques;
import com.example.talit.projetotcc.logicalView.CategoriasProdutos;
import com.example.talit.projetotcc.sqlight.DbConn;

import java.lang.reflect.Field;
import java.util.List;

import static com.example.talit.projetotcc.fragments.TabDestaques.dialogo;

public class PaginaInicialEstabelecimentos extends AppCompatActivity implements Categorias.Listener {

    public static String categorias;
    public static String destaques;
    public static String nomeEstab;
    public static RecyclerView recCategorias;
    public static RelativeLayout no_categoria;
    public static ProgressBar pb;
    private DbConn dbconn;
    private FloatingActionButton btnCarrinho;
    private TextView txtCount;
    BottomNavigationView navigation;
    public static Activity act;
    public static Context context;
    private Toolbar toolbar;
    private Toolbar toolbar2;
    public static CoordinatorLayout searchViewLayout;
    private CoordinatorLayout coordDetalhes;
    public static ProgressBar pbCateg;
    public ImageButton imFiltro;
    private boolean houveEscolha;
    public static ProgressBar pbMarcas;
    public static ProgressBar pbSubcategorias;
    public static RelativeLayout no_marcas;
    public static RelativeLayout no_sub;
    public static RecyclerView recMarca;
    public static RecyclerView recSubcategorias;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_pagina_inicial_estabelecimentos);
        act = this;
        context = this;
        //getSupportActionBar().setElevation(0);

        //view = (ViewPager) findViewById(R.id.view_pager);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        //toolbar2 = (Toolbar) findViewById(R.id.toolbar_det);
        searchViewLayout = (CoordinatorLayout) findViewById(R.id.coordInicio);
        coordDetalhes = (CoordinatorLayout)findViewById(R.id.coordDetalhes);
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        pb = (ProgressBar) findViewById(R.id.pb_localizaçao);
        btnCarrinho = (FloatingActionButton) findViewById(R.id.btn_carrinho);
        txtCount = (TextView) findViewById(R.id.notificacao);
        no_categoria = (RelativeLayout) findViewById(R.id.rl_no_catgeorias);
        recCategorias = (RecyclerView) findViewById(R.id.rec_categ);
        pbCateg = (ProgressBar) findViewById(R.id.pb_categorias);
        imFiltro = (ImageButton) findViewById(R.id.imgfiltroEstab);
        pb = (ProgressBar) findViewById(R.id.pb_localizaçao);

        pbCateg.setVisibility(View.INVISIBLE);
        coordDetalhes.setVisibility(View.GONE);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0);
        dbconn = new DbConn(PaginaInicialEstabelecimentos.this);


        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recCategorias.setLayoutManager(layoutManager);

        Categorias conn = new Categorias(null);
        conn.execute();

        if(nomeEstab != null) {
            getSupportActionBar().setTitle(nomeEstab);
        }else{
            getSupportActionBar().setTitle("Estabelecimento");
        }

        if(dbconn.totalItensCarrinho() > 0){
            txtCount.setVisibility(View.VISIBLE);
            txtCount.setText(dbconn.totalItensCarrinho()+"");
        }else{
            txtCount.setVisibility(View.INVISIBLE);
        }

        replaceFragment(new TabDestaques());
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        BottomNavigationViewHelper.disableShiftMode(navigation);
        btnCarrinho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PaginaInicialEstabelecimentos.this, Carrinho.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
                finish();

            }
        });
        imFiltro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fltroSupermercado();
            }
        });

    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_bottom_estabelecimento, menu);
        return true;
    }*/

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        //startActivity(new Intent(PaginaInicialEstabelecimentos.this, PaginalnicialConsumidor.class));
        //finish();

        AlertDialog.Builder builder = new AlertDialog.Builder(PaginaInicialEstabelecimentos.this);
        builder.setTitle("");
        builder.setMessage("Você tem Certeza que deseja sair dessa Loja?");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(PaginaInicialEstabelecimentos.this, PaginalnicialConsumidor.class));
                dialog.dismiss();
                finish();
                onBackPressed();

               /*ver depois
               if (dbconn.selectProutos().size() > 0) {
                   dbconn.deleteSacola();
                   startActivity(new Intent(PaginaInicialEstabelecimentos.this, PaginalnicialConsumidor.class));
                   dialog.dismiss();
                   finish();
               }else{
                   startActivity(new Intent(PaginaInicialEstabelecimentos.this, PaginalnicialConsumidor.class));
                   dialog.dismiss();
                   finish();
               }*/

            }
        });
        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                break;
        }
        return true;
    }
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                /*case R.id.action_depart:
                    replaceFragment(new TabCategorias());
                    searchViewLayout.setVisibility(View.INVISIBLE);
                    //fragmentTransaction.replace(R.id.content, new DetalhesEstab()).commit();

                    return true;*/

                case R.id.action_info:
                    coordDetalhes.setVisibility(View.VISIBLE);
                    searchViewLayout.setVisibility(View.GONE);
                    replaceFragment(new DetalhesEstab());
                    //fragmentTransaction.replace(R.id.content, new DetalhesEstab()).commit();
                    return true;

                case R.id.action_avaliações:
                    searchViewLayout.setVisibility(View.GONE);
                    coordDetalhes.setVisibility(View.GONE);
                    startActivity(new Intent(PaginaInicialEstabelecimentos.this,SobreLoja.class));
                    finish();
                    break;

                case R.id.action_inicio:
                     searchViewLayout.setVisibility(View.VISIBLE);
                    coordDetalhes.setVisibility(View.GONE);
                    replaceFragment(new TabDestaques());
                    //fragmentTransaction.replace(R.id.content, new TabDestaques()).commit();
                    return true;
            }

            return false;
        }

    };

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.content, fragment);
        transaction.commit();
    }

    @Override
    public void onLoaded(List<CategoriasProdutos> categs) {

        CategoriasAdapter categoriasAdapter = new CategoriasAdapter(this, categs);
        recCategorias.setAdapter(categoriasAdapter);
        categoriasAdapter.notifyDataSetChanged();
    }

   /* @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_cestinha:
                break;
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                break;
        }
        return true;
    }*/

    public static class BottomNavigationViewHelper {
        public static void disableShiftMode(BottomNavigationView view) {
            BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
            try {
                Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
                shiftingMode.setAccessible(true);
                shiftingMode.setBoolean(menuView, false);
                shiftingMode.setAccessible(false);
                for (int i = 0; i < menuView.getChildCount(); i++) {
                    BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                    //noinspection RestrictedApi
                    item.setShiftingMode(false);
                    // set once again checked value, so view will be updated
                    //noinspection RestrictedApi
                    item.setChecked(item.getItemData().isChecked());
                }
            } catch (NoSuchFieldException e) {
                Log.e("BNVHelper", "Unable to get shift mode field", e);
            } catch (IllegalAccessException e) {
                Log.e("BNVHelper", "Unable to change value of shift mode", e);
            }
        }
    }
    public void fltroSupermercado() {

        LayoutInflater inflater = getLayoutInflater();
        final View alertLayout = inflater.inflate(R.layout.custom_alerta_dialog_subcategorias, null);
        Button cancelar = (Button) alertLayout.findViewById(R.id.cancelar);
        recMarca = (RecyclerView)alertLayout.findViewById(R.id.rec_marcas);
        recSubcategorias = (RecyclerView)alertLayout.findViewById(R.id.rec_categ);
        pbMarcas = (ProgressBar) alertLayout.findViewById(R.id.pb_marcas);
        pbSubcategorias = (ProgressBar)alertLayout.findViewById(R.id.pb_sub);
        no_sub = (RelativeLayout)alertLayout.findViewById(R.id.no_sub);
        no_marcas = (RelativeLayout)alertLayout.findViewById(R.id.no_marcas);

        pbSubcategorias.setVisibility(View.INVISIBLE);
        pbMarcas.setVisibility(View.INVISIBLE);

        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setView(alertLayout);
        alerta.setCancelable(false);
        dialogo = alerta.create();
        dialogo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogo.show();

        /*Marcas connMarca= new Marcas(null);
        connMarca.execute();*/

       /* Subcategorias connSub = new Subcategorias(null);
        connSub.execute();*/

        LinearLayoutManager layoutManagerMarca = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recMarca.setLayoutManager(layoutManagerMarca);

        LinearLayoutManager layoutManagerSub = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recSubcategorias.setLayoutManager(layoutManagerSub);

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogo.dismiss();
            }

        });
    }
}
