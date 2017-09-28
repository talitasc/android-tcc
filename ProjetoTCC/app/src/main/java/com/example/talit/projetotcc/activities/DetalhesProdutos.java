package com.example.talit.projetotcc.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.talit.projetotcc.R;
import com.example.talit.projetotcc.sqlight.DbConn;

public class DetalhesProdutos extends AppCompatActivity {

    private TextView nomeProd;
    private TextView marca;
    private TextView preco;
    private TextView prazoValidade;
    private TextView informacoes;
    private TextView codRef;
    private ImageView imProduto;
    private Button btnAdicionar;
    private String strnomeProd;
    private String strMarca;
    private String strPreco;
    private String strCodRef;
    private DbConn dbconn;
    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private Snackbar snackbar;
    private View snackbarView;
    private CoordinatorLayout cord;
    public static Bitmap b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_detalhes_produtos);
        //imProduto = (ImageView) findViewById(R.id.ic_produto);
        nomeProd = (TextView) findViewById(R.id.txtNomeProduto);
        marca = (TextView) findViewById(R.id.txt_marca_prod);
        preco = (TextView) findViewById(R.id.txt_preco);
        prazoValidade = (TextView) findViewById(R.id.txt_prazo_validade);
        informacoes = (TextView) findViewById(R.id.txt_informacoes);
        codRef = (TextView) findViewById(R.id.txt_cod);
        btnAdicionar  = (Button)findViewById(R.id.btn_adiconar_carrinho);
        collapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.toolbar_layout);
        cord = (CoordinatorLayout)findViewById(R.id.act_detalhes_produtos);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setTitle("Detalhes do Produto");
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        //getSupportActionBar().setHomeButtonEnabled(true);

        dbconn = new DbConn(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbconn = new DbConn(this);

        if (getIntent().hasExtra("nomeProduto") && getIntent().hasExtra("img") &&
                getIntent().hasExtra("marcaProduto") && getIntent().hasExtra("precoProduto")
                && getIntent().hasExtra("prazoProduto") && getIntent().hasExtra("infosProduto")&& getIntent().hasExtra("codRef")) {

            strnomeProd = getIntent().getStringExtra("nomeProduto");
            strMarca = getIntent().getStringExtra("marcaProduto");
            strPreco= getIntent().getStringExtra("precoProduto");
            strCodRef = getIntent().getStringExtra("codRef");


            nomeProd.setText(getIntent().getStringExtra("nomeProduto"));
            marca.setText("Marca: " + getIntent().getStringExtra("marcaProduto"));
            preco.setText("R$"+ getIntent().getStringExtra("precoProduto"));
            prazoValidade.setText(getIntent().getStringExtra("prazoProduto"));
            informacoes.setText(getIntent().getStringExtra("infosProduto"));
            codRef.setText(getIntent().getStringExtra("codRef"));
            b = BitmapFactory.decodeByteArray(getIntent().getByteArrayExtra("img"), 0, getIntent().getByteArrayExtra("img").length);
            //imProduto.setImageBitmap(b);
            //dbconn.insertSacola(strnomeProd,strMarca,strPreco,b);
            //dbconn.insertConsumidor(mac.getIdCons(), mac.getUsuario(), mac.getSenha(), mac.getStatus(), mac.getTpAcesso());
        }
        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (dbconn.selectIdProduto(strnomeProd) != null ){
                    CreateSnackbar("Este produto já existe em seu carrinho.");
                }else{
                    dbconn.insertSacola(strnomeProd,strMarca,Double.parseDouble(strPreco.replace("R$","")),b,strCodRef);
                    //dbconn.insertSacola(strnomeProd,strMarca,strPreco,b,strCodRef);
                    CreateSnackbar("Este produto foi adicionado ao carrinho");
                }

                /*Intent intent = new Intent();
                intent.setClass(DetalhesProdutos_1.this,Carrinho.class);
                intent.putExtra("nomeProduto",strnomeProd);
                intent.putExtra("marcaProduto",strMarca);
                intent.putExtra("precoProduto",strPreco);
                DetalhesProdutos_1.this.startActivity(intent);
                finishActivity(0);*/
            }
        });

       /*Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
        R.drawable.ic_info_outline_black_24dp);
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                collapsingToolbarLayout.setContentScrimColor(palette.getMutedColor(R.attr.colorPrimary));
                collapsingToolbarLayout.setStatusBarScrimColor(palette.getMutedColor(R.attr.colorPrimaryDark));
            }
        });*/

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
    public void CreateSnackbar(String mensagem){

        Snackbar.make(cord, mensagem, Snackbar.LENGTH_LONG)
                .setAction("Ok", null).show();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(DetalhesProdutos.this, ProdutosEstabelecimento.class));
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
