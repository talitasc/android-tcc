package com.example.talit.projetotcc.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.talit.projetotcc.R;
import com.example.talit.projetotcc.sqlight.DbConn;

/**
 * Created by talit on 29/07/2017.
 */

public class Avaliacoes extends AppCompatActivity {

    public static RelativeLayout no_avaliacoes;
    public static RelativeLayout no_Cadastrado;
    private DbConn dbconn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frag_avaliacoes_estabelecimento);
        no_avaliacoes = (RelativeLayout)findViewById(R.id.rl_nolist);
        no_Cadastrado = (RelativeLayout)findViewById(R.id.rl_noCdastrado);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Avaliações");

        dbconn = new DbConn(Avaliacoes.this);
        if (dbconn.selectConsumidor().getTpAcesso() == 8){
            no_Cadastrado.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            default:break;
        }
        return true;
    }
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(Avaliacoes.this, PaginaInicialEstabelecimentos.class));
        finish();
    }
}
