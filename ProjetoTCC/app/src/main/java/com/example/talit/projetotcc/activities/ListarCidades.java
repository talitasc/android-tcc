package com.example.talit.projetotcc.activities;

import android.app.Activity;
import android.app.SearchManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.talit.projetotcc.R;
import com.example.talit.projetotcc.adapters.CidadeAdapter;
import com.example.talit.projetotcc.connectionAPI.Cidades;
import com.example.talit.projetotcc.connectionAPI.Estados;
import com.example.talit.projetotcc.logicalView.Cidade;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ListarCidades extends AppCompatActivity implements Cidades.Listener,SearchView.OnQueryTextListener{

    public static ListView listas;
    public static Context context;
    public static Activity act;
    public static ProgressBar pbLocais;
    public static  ArrayList<Cidade> cids = new ArrayList<Cidade>();
    public static int idEstado;
    public static  CidadeAdapter cidadeAdapter;
    private SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_listar_cidades);

        context = this;
        act = this;
        listas = (ListView) findViewById(R.id.listarLocais);
        pbLocais = (ProgressBar) findViewById(R.id.pb_localizacao);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle(getString(R.string.select_cidade));

        pbLocais.setVisibility(View.VISIBLE);

        Cidades conn = new Cidades(null);
        conn.execute(idEstado + "");

    }
    public void onLoaded(List<Cidade> listarCidade) {

        CidadeAdapter listarCidades = new CidadeAdapter(ListarCidades.this,ListarCidades.this, listarCidade);
        this.listas.setAdapter(listarCidades);
        this.listas.deferNotifyDataSetChanged();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.searchview,menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);


        return true;
    }
    @Override
    public boolean onQueryTextSubmit(String query) {

        query = query.toLowerCase();
        ArrayList<Cidade>filtros = new ArrayList<>();

        for(Cidade cid : cids){
            String nome  = cid.getDescricaoCidade().toLowerCase();
            if(nome.contains(query)){
                filtros.add(cid);
            }
        }

        cidadeAdapter.setFilter(filtros);
        return true;

    }

    @Override
    public boolean onQueryTextChange(String newText) {
        newText = newText.toLowerCase();
        ArrayList<Cidade>filtros = new ArrayList<>();

        for(Cidade cid : cids){
            String nome  = cid.getDescricaoCidade().toLowerCase();
            if(nome.contains(newText)){
                filtros.add(cid);
            }
        }

        cidadeAdapter.setFilter(filtros);
        return true;
    }


    public void onResume(){
        super.onResume();
        List<Cidade> listarCidade = new ArrayList<>();
        CidadeAdapter listaCid = new CidadeAdapter(ListarCidades.this,ListarCidades.this, listarCidade);
        this.listas.setAdapter(listaCid);
        this.listas.deferNotifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ListarCidades.this, ListarEstados.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
        finish();

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


}
