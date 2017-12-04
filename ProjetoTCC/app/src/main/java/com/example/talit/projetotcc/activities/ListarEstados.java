package com.example.talit.projetotcc.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

import com.example.talit.projetotcc.R;
import com.example.talit.projetotcc.adapters.CidadeAdapter;
import com.example.talit.projetotcc.adapters.EstadoAdapter;
import com.example.talit.projetotcc.connectionAPI.Estados;
import com.example.talit.projetotcc.logicalView.Cidade;
import com.example.talit.projetotcc.logicalView.Estado;

import java.util.ArrayList;
import java.util.List;

public class ListarEstados extends AppCompatActivity implements Estados.Listener,SearchView.OnQueryTextListener{
    public static ListView listas;
    public static Context context;
    public static Activity act;
    public static ProgressBar pbLocais;
    public static EstadoAdapter estadoAdapter;
    public static  ArrayList<Estado> ests = new ArrayList<Estado>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_listar_locais);
        context = this;
        act = this;
        listas = (ListView) findViewById(R.id.listarLocais);
        pbLocais = (ProgressBar) findViewById(R.id.pb_localizacao);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle(getString(R.string.select_estado));
        pbLocais.setVisibility(View.VISIBLE);

        Estados conn = new Estados(null);
        conn.execute();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.searchview,menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);
        return true;
    }
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        newText = newText.toLowerCase();
        ArrayList<Estado>filtros = new ArrayList<>();

        for(Estado est : ests){
            String nome  = est.getDescricaoEstado().toLowerCase();
            if(nome.contains(newText)){
                filtros.add(est);
            }
        }

        estadoAdapter.setFilter(filtros);
        return true;
    }
    public void onLoaded(List<Estado> listasEstados) {

        EstadoAdapter listaSuper = new EstadoAdapter(ListarEstados.this,ListarEstados.this, listasEstados);
        this.listas.setAdapter(listaSuper);
        this.listas.deferNotifyDataSetChanged();

    }
    @Override
    public void onResume(){
        super.onResume();
        List<Estado> listarEstado = new ArrayList<>();
        EstadoAdapter listaSuper = new EstadoAdapter(ListarEstados.this,ListarEstados.this, listarEstado);
        this.listas.setAdapter(listaSuper);
        this.listas.deferNotifyDataSetChanged();
    }

    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ListarEstados.this, PaginalnicialConsumidor.class);
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
