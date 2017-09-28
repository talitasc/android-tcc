package com.example.talit.projetotcc.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.talit.projetotcc.R;
import com.example.talit.projetotcc.adapters.CarrinhoAdapter;

import com.example.talit.projetotcc.logicalView.Produtos;
import com.example.talit.projetotcc.sqlight.DbConn;

import java.util.ArrayList;

public class Carrinho extends AppCompatActivity {

    public static ListView listas;
    public static Context context;
    public static Activity act;
    public static ProgressBar pbLocais;
    private ArrayList<Produtos> produtos = new ArrayList<Produtos>();
    private DbConn dbconn;
    public static RelativeLayout no_list;
    public static CardView cardFinal;
    public static Button btnFinal;
    public static TextView txtValorTotal;
    public static TextView txtQtd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_carrinho);
        context = this;
        act = this;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Sacola");
        listas = (ListView) findViewById(R.id.lv_carrinho);
        no_list = (RelativeLayout) findViewById(R.id.rl_nolist);
        cardFinal = (CardView)findViewById(R.id.cardView);
        btnFinal = (Button)findViewById(R.id.btn_adiconar_carrinho);
        txtValorTotal = (TextView) findViewById(R.id.txtValorTotalDouble);
        txtQtd = (TextView) findViewById(R.id.txtQtd);


        dbconn = new DbConn(this);

       Intent it = getIntent();
        String nome_del = "";
       if (it.getStringExtra("NOME_DEL") != null) {
            nome_del = it.getStringExtra("NOME_DEL");
            if (nome_del != "") {
                Toast.makeText(getBaseContext(),nome_del, Toast.LENGTH_SHORT).show();

                Toast.makeText(getBaseContext(),"itens" + dbconn.selectIdProduto(nome_del).getCodReferencia(), Toast.LENGTH_SHORT).show();
                //dbconn.selectIdProduto(nome_del).getIdProduto();
                dbconn.deleteCarrinhoId("14");
            }
        }

        if (dbconn.selectProutos() != null) {
            CarrinhoAdapter carAdapter = new CarrinhoAdapter(Carrinho.this, Carrinho.this, dbconn.selectProutos());
            listas.setAdapter(carAdapter);
            listas.deferNotifyDataSetChanged();
            txtValorTotal.setText("R$ " + dbconn.totalCarrinho());
            txtQtd.setText(""+ dbconn.totalItensCarrinho());

            //int count = dbconn.totalItensCarrinho();
            //Toast.makeText(getBaseContext(),"itens" + count, Toast.LENGTH_SHORT).show();
        }
        if(listas.getCount() == 0){
            no_list.setVisibility(View.VISIBLE);
            cardFinal.setVisibility(View.INVISIBLE);
            btnFinal.setVisibility(View.INVISIBLE);
        }
        btnFinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Carrinho.this, FinalizarCompra.class));
                finish();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_lateral_carrinho, menu);
        return true;
    }

    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(Carrinho.this, PaginaInicialEstabelecimentos.class));
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.menu_excluir){
            dbconn.deleteSacola();
            CarrinhoAdapter carAdapter = new CarrinhoAdapter(Carrinho.this, Carrinho.this, dbconn.selectProutos());
            listas.setAdapter(carAdapter);
            listas.deferNotifyDataSetChanged();
            no_list.setVisibility(View.VISIBLE);
            cardFinal.setVisibility(View.INVISIBLE);
            btnFinal.setVisibility(View.INVISIBLE);

        }else if (id == android.R.id.home){
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }
}
