package com.example.talit.projetotcc.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.example.talit.projetotcc.R;
import com.example.talit.projetotcc.connectionAPI.ListarPedidos;
import com.example.talit.projetotcc.logicalView.Pedido;
import com.example.talit.projetotcc.sqlight.DbConn;

public class Pedidos extends AppCompatActivity {

    public static ProgressBar pbPedidos;
    public static RecyclerView recView;
    public static RelativeLayout relativeNoped;
    public static Activity act;
    public static Context contex;
    private DbConn dbConn;
    private String tpUser;
    private String idUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_pedidos);
        act = this;
        contex = this;
        pbPedidos = (ProgressBar) findViewById(R.id.pb_pedidos);
        recView = (RecyclerView) findViewById(R.id.lv_pedido);
        relativeNoped = (RelativeLayout) findViewById(R.id.rl_noende);
        pbPedidos.setVisibility(View.INVISIBLE);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recView.setLayoutManager(layoutManager);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.txt_sseus_pedido));

        dbConn = new DbConn(Pedidos.this);
        idUser = dbConn.selectConsumidor().getIdCons() + "";
        tpUser = dbConn.selectConsumidor().getTpAcesso() + "";

        ListarPedidos connListar = new ListarPedidos();
        connListar.execute(idUser, tpUser);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Pedidos.this, PaginalnicialConsumidor.class);
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