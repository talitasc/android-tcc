package com.example.talit.projetotcc.activities;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.talit.projetotcc.R;

public class RedirecionaPessoaJuridica extends AppCompatActivity {

    private ImageView imgPessoaFis;
    private ImageView imPessoaJur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_redireciona_pessoa_juridica);

        imgPessoaFis = (ImageView) findViewById(R.id.im_pessoa_fis);
        imPessoaJur = (ImageView) findViewById(R.id.im_pessoa_jur);

        imgPessoaFis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RedirecionaPessoaJuridica.this, LoginCliente.class));
                finish();
            }
        });
        imPessoaJur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RedirecionaPessoaJuridica.this, LoginPessoaJuridica.class));
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(RedirecionaPessoaJuridica.this, WelcomeScreen.class));
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