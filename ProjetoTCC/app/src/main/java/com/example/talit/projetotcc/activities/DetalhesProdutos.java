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
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.talit.projetotcc.R;
import com.example.talit.projetotcc.sqlight.DbConn;
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
    private String strnomeProd;
    private String strMarca;
    private String strPreco;
    private String strCodRef;
    private String strImagem;
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
        imProduto = (SimpleDraweeView) findViewById(R.id.id_logo_produto);
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
        getSupportActionBar().setTitle("Detalhes do produto");
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        //getSupportActionBar().setHomeButtonEnabled(true);

        dbconn = new DbConn(this);
        if (getIntent().hasExtra("nomeProduto") && getIntent().hasExtra("img") &&
                getIntent().hasExtra("marcaProduto") && getIntent().hasExtra("precoProduto")
                && getIntent().hasExtra("prazoProduto") && getIntent().hasExtra("infosProduto")&& getIntent().hasExtra("codRef")) {

            strnomeProd = getIntent().getStringExtra("nomeProduto");
            strMarca = getIntent().getStringExtra("marcaProduto");
            strPreco= getIntent().getStringExtra("precoProduto");
            strCodRef = getIntent().getStringExtra("codRef");
            strImagem = getIntent().getStringExtra("img");

            nomeProd.setText(strnomeProd);
            marca.setText(strMarca);
            preco.setText(String.format("R$%s", strPreco));
            prazoValidade.setText(getIntent().getStringExtra("prazoProduto"));
            informacoes.setText(getIntent().getStringExtra("infosProduto"));
            codRef.setText(getIntent().getStringExtra("codRef"));
            imProduto.setImageBitmap(convert(strImagem));

            //dbconn.insertSacola(strnomeProd,strMarca,strPreco,b);
            //dbconn.insertConsumidor(mac.getIdCons(), mac.getUsuario(), mac.getSenha(), mac.getStatus(), mac.getTpAcesso());
        }
        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*ve depois
                if (dbconn.selectIdProduto(strnomeProd) != null ){
                    CreateSnackbar("Este produto já existe em seu carrinho.");
                }else{
                    dbconn.insertSacola(strnomeProd,strMarca,Double.parseDouble(strPreco.replace("R$","")),b,strCodRef);
                    //dbconn.insertSacola(strnomeProd,strMarca,strPreco,b,strCodRef);
                    CreateSnackbar("Este produto foi adicionado ao carrinho");
                }*/

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
