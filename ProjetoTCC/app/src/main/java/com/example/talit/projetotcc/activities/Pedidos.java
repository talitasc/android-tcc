package com.example.talit.projetotcc.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.example.talit.projetotcc.R;

public class Pedidos extends AppCompatActivity {

    private ProgressBar pbPedidos;
    private RecyclerView recView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_pedidos);
        pbPedidos = (ProgressBar)findViewById(R.id.pb_pedidos);
        recView = (RecyclerView)findViewById(R.id.lv_pedido);
        pbPedidos.setVisibility(View.INVISIBLE);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recView.setLayoutManager(layoutManager);

    }
}
