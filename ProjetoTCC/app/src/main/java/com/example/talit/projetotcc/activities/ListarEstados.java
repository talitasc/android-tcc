package com.example.talit.projetotcc.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.talit.projetotcc.R;
import com.example.talit.projetotcc.adapters.EstadoAdapter;
import com.example.talit.projetotcc.connectionAPI.Estados;
import com.example.talit.projetotcc.logicalView.Estado;

import java.util.ArrayList;
import java.util.List;

public class ListarEstados extends AppCompatActivity implements Estados.Listener{
    public static ListView listas;
    public static Context context;
    public static Activity act;
    public static ProgressBar pbLocais;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_listar_locais);
        context = this;
        act = this;
        listas = (ListView) findViewById(R.id.listarLocais);
        pbLocais = (ProgressBar) findViewById(R.id.pb_localizaçao);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle(getString(R.string.select_estado));
        pbLocais.setVisibility(View.VISIBLE);

        Estados conn = new Estados(null);
        conn.execute();
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
