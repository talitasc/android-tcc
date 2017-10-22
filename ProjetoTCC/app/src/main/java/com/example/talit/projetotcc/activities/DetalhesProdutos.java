package com.example.talit.projetotcc.activities;

import android.content.Context;
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
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.talit.projetotcc.R;
import com.example.talit.projetotcc.connectionAPI.AtualizaCarrinho;
import com.example.talit.projetotcc.connectionAPI.CriaCarrinho;
import com.example.talit.projetotcc.fragments.DetalhesEstab;
import com.example.talit.projetotcc.logicalView.Estabelecimento;
import com.example.talit.projetotcc.sqlight.DbConn;
import com.example.talit.projetotcc.utils.Validacoes;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.ByteArrayOutputStream;

public class DetalhesProdutos extends AppCompatActivity {

    private TextView nomeProd;
    private TextView marca;
    private TextView preco;
    private TextView prazoValidade;
    private TextView informacoes;
    private TextView codRef;
    private SimpleDraweeView imProduto;
    private Button btnAdicionar;
    public static String strnomeProd;
    public static String strMarca;
    public static String strPreco;
    public static String strIdProd;
    public static String strImagem;
    private DbConn dbconn;
    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private Snackbar snackbar;
    private View snackbarView;
    public static CoordinatorLayout cord;
    public static Context c;
    public static ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_detalhes_produtos);
        c= this;
        imProduto = (SimpleDraweeView) findViewById(R.id.id_logo_produto);
        nomeProd = (TextView) findViewById(R.id.txtNomeProduto);
        marca = (TextView) findViewById(R.id.txt_marca_prod);
        preco = (TextView) findViewById(R.id.txt_preco);
        prazoValidade = (TextView) findViewById(R.id.txt_prazo_validade);
        informacoes = (TextView) findViewById(R.id.txt_informacoes);
        codRef = (TextView) findViewById(R.id.txt_cod);
        btnAdicionar = (Button) findViewById(R.id.btn_adiconar_carrinho);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        cord = (CoordinatorLayout) findViewById(R.id.act_detalhes_produtos);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        pb = (ProgressBar) findViewById(R.id.pb_detalhes_prod);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Detalhes do produto");
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        //getSupportActionBar().setHomeButtonEnabled(true);

        dbconn = new DbConn(this);
        if (getIntent().hasExtra("nomeProduto") && getIntent().hasExtra("img") &&
                getIntent().hasExtra("marcaProduto") && getIntent().hasExtra("precoProduto")
                && getIntent().hasExtra("prazoProduto") && getIntent().hasExtra("infosProduto") && getIntent().hasExtra("idLote")) {

            strnomeProd = getIntent().getStringExtra("nomeProduto");
            strMarca = getIntent().getStringExtra("marcaProduto");
            strPreco = getIntent().getStringExtra("precoProduto");
            strIdProd = getIntent().getStringExtra("idLote");
            strImagem = getIntent().getStringExtra("img");

            nomeProd.setText(strnomeProd);
            marca.setText(strMarca);
            preco.setText(String.format("R$ %s", strPreco));
            prazoValidade.setText(getIntent().getStringExtra("prazoProduto"));
            informacoes.setText(getIntent().getStringExtra("infosProduto"));
            codRef.setText(strIdProd);
            imProduto.setImageBitmap(convert(strImagem));
        }
        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (dbconn.selectIdProduto(Integer.parseInt(strIdProd)) != null) {
                    Validacoes.showSnackBar(getBaseContext(),cord,"Este produto já existe em seu carrinho.");
                } else {
                        if(dbconn.totalItensCarrinho()<=0){
                            CriaCarrinho connCria = new CriaCarrinho(null);
                            connCria.execute(String.format("%d", dbconn.selectConsumidor().getIdCons()),
                                    String.format("%d", dbconn.selectConsumidor().getTpAcesso()),
                                    DetalhesEstab.strIdEstab,
                                    strIdProd,
                                    "1");

                            //Toast.makeText(Carrinho.context, "cria" , Toast.LENGTH_SHORT).show();
                        }else{
                            AtualizaCarrinho connAtualiza = new AtualizaCarrinho(null);
                            connAtualiza.execute(String.format("%d", dbconn.selectConsumidor().getIdCons()),
                                    String.format("%d", dbconn.selectConsumidor().getTpAcesso()),
                                    DetalhesEstab.strIdEstab,
                                    strIdProd,
                                    "1");
                            //Toast.makeText(Carrinho.context,"atualiza", Toast.LENGTH_SHORT).show();
                        }
                   // dbconn.insertSacola(Integer.parseInt(strIdProd), Integer.parseInt(strIdProd),
                            //strnomeProd, strMarca, Double.parseDouble(strPreco.replace("R$", "")), 2, strImagem);
                    //Validacoes.showSnackBar(getBaseContext(),cord,"Este produto foi adicionado ao carrinho");
                }
            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }


    public static Bitmap convert(String base64Str) throws IllegalArgumentException {
        byte[] decodedBytes = Base64.decode(
                base64Str.substring(base64Str.indexOf(",") + 1),
                Base64.DEFAULT
        );
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }

    public static String convert(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);

        return Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(DetalhesProdutos.this, ProdutosEstabelecimento.class));
        finish();

    }

    /*public void onLoaded(String string) {
        if (string.equalsIgnoreCase("true")) {
            pb.setVisibility(View.VISIBLE);
            WelcomeScreen.act.finish();
            startActivity(new Intent(this, LoginCliente.class));
            finish();
        } else {
            pb.setVisibility(View.INVISIBLE);
        }
    }*/

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
