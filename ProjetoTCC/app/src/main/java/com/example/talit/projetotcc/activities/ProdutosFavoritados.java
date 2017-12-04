package com.example.talit.projetotcc.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.example.talit.projetotcc.R;
import com.example.talit.projetotcc.adapters.ProdutosFavoritosAdapter;
import com.example.talit.projetotcc.connectionAPI.ListarProdutosFavoritos;
import com.example.talit.projetotcc.sqlight.DbConn;

public class ProdutosFavoritados extends AppCompatActivity {
    public static RecyclerView listas;
    public static RelativeLayout relativeNoList;
    public static ProgressBar progressBar;
    public static Activity act;
    public static Context c;
    private DbConn dbConn;
    private String tpUser;
    private String idUser;
    public static ProdutosFavoritosAdapter produtosFavoritosAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_produtos_favoritados);
        act = this;
        c = this;
        listas = (RecyclerView)findViewById(R.id.lv_favoritos);
        relativeNoList = (RelativeLayout)findViewById(R.id.rl_nolist);
        progressBar= (ProgressBar)findViewById(R.id.pb_favoritos);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.produtos_favoritos_valida_titulo));

        listas.setHasFixedSize(true);
        StaggeredGridLayoutManager llm = new StaggeredGridLayoutManager(1, 1);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        listas.setLayoutManager(llm);

        dbConn = new DbConn(ProdutosFavoritados.this);
        idUser = dbConn.selectConsumidor().getIdCons() + "";
        tpUser = dbConn.selectConsumidor().getTpAcesso() + "";

        ListarProdutosFavoritos connFavoritos = new ListarProdutosFavoritos(null);
        connFavoritos.execute(idUser, tpUser);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ProdutosFavoritados.this, PaginalnicialConsumidor.class);
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
            default:break;
        }
        return true;
    }

}
