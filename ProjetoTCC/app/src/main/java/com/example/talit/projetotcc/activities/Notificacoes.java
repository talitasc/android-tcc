package com.example.talit.projetotcc.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.example.talit.projetotcc.R;

public class Notificacoes extends AppCompatActivity {

    private CheckBox checkTodos;
    private CheckBox notificações;
    private CheckBox promocoes;
    private CheckBox favoritos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_notificacoes);
        checkTodos = (CheckBox) findViewById(R.id.checkSmarket);
        notificações = (CheckBox) findViewById(R.id.checkNot);
        promocoes = (CheckBox) findViewById(R.id.checkNotFavorito);
        favoritos = (CheckBox) findViewById(R.id.checkPromo);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.txt_configurar_notificacoes));

        checkTodos.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(checkTodos.isChecked()){
                    notificações.setChecked(true);
                    promocoes.setChecked(true);
                    favoritos.setChecked(true);
                }else{
                    notificações.setChecked(false);
                    promocoes.setChecked(false);
                    favoritos.setChecked(false);
                }
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Notificacoes.this, PaginalnicialConsumidor.class);
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
