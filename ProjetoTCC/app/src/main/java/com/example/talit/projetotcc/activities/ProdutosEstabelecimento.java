package com.example.talit.projetotcc.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.talit.projetotcc.R;
import com.example.talit.projetotcc.adapters.ProdutosAdapter;
import com.example.talit.projetotcc.connectionAPI.LotePorCategoria;
import com.example.talit.projetotcc.logicalView.Produtos;
import com.example.talit.projetotcc.sqlight.DbConn;

import java.util.ArrayList;
import java.util.List;

public class ProdutosEstabelecimento extends AppCompatActivity implements LotePorCategoria.Listener {

    public static RecyclerView rec;
    private FloatingActionButton btnCarrinho;
    private ArrayList<Produtos> produtos = new ArrayList<Produtos>();
    public static RelativeLayout no_Cadastrado;
    private DbConn dbconn;
    private TextView txtCount;
    public static ProgressBar pb;
    public static Activity act;
    public static Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_prod_estabelecimento);
        act = this;
        context = this;
        rec = (RecyclerView) findViewById(R.id.lv_prod);
        btnCarrinho = (FloatingActionButton) findViewById(R.id.btn_carrinho);
        no_Cadastrado = (RelativeLayout)findViewById(R.id.rl_noCdastrado);
        txtCount = (TextView) findViewById(R.id.notificacao);
        pb = (ProgressBar) findViewById(R.id.pb_produtos);
        pb.setVisibility(View.INVISIBLE);

        rec.setHasFixedSize(true);
        getSupportActionBar().setTitle("estabelecimento");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);

        StaggeredGridLayoutManager llm = new StaggeredGridLayoutManager(2, 1);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rec.setLayoutManager(llm);

        dbconn = new DbConn(ProdutosEstabelecimento.this);

        /*produtos.add(new Produtos(R.drawable.logo_super, 1, "Bolacha Recheada", "passatempo", 00.50, "Passatempo sabor Morango com 143g Nabisco. Para valores nutricionais estão informados no verso do produto.", "Validade:20/06/2017","12"));
        produtos.add(new Produtos(R.drawable.logo_super, 2, "Biscoito Salgado", "Pit Stop", + 1.75, "Biscoito agua e sal Pit stop com 9 Unidades.Para valores nutricionais estão informados no verso do produto.", "Validade:20/06/2017","13"));
        produtos.add(new Produtos(R.drawable.logo_super, 3, "Creme de Amendoin", "Paçoquita", 2.00, "Paçoquita cremosa 180G.Para valores nutricionais estão informados no verso do produto", "Validade:20/06/2017","14"));*/

        /*ver depois
        if (dbconn.selectConsumidor().getTpAcesso() == 8) {
            no_Cadastrado.setVisibility(View.VISIBLE);
            btnCarrinho.setVisibility(View.INVISIBLE);
        } else {
            rec.setAdapter(new ProdutosAdapter(produtos, this));
            rec.setAdapter(new ProdutosAdapter(produtos, this));
        }*/

        if(dbconn.totalItensCarrinho() > 0){
            txtCount.setVisibility(View.VISIBLE);
            txtCount.setText(dbconn.totalItensCarrinho()+"");
        }else{
            txtCount.setVisibility(View.INVISIBLE);
        }

        LotePorCategoria conn = new LotePorCategoria(null);
        conn.execute("5");
        btnCarrinho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProdutosEstabelecimento.this, Carrinho.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
                finish();

            }
        });
    }

    public void onLoaded(List<Produtos> prods) {
        ProdutosAdapter produtosAdapter  = new ProdutosAdapter(prods,ProdutosEstabelecimento.this, ProdutosEstabelecimento.this);
        ProdutosEstabelecimento.rec.setAdapter(produtosAdapter);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ProdutosEstabelecimento.this, PaginaInicialEstabelecimentos.class);
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
