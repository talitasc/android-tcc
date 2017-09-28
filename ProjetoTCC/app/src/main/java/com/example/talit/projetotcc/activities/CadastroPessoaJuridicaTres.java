package com.example.talit.projetotcc.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.talit.projetotcc.R;
import com.example.talit.projetotcc.Validacoes.Validacoes;

public class CadastroPessoaJuridicaTres extends AppCompatActivity {

    private Button btnCadastrar;
    private EditText edtEmail;
    private EditText edtTelefone;
    private String strEmail;
    private String strTelefone;
    private boolean haEmail;
    private boolean haTelefone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_cadastro_pessoa_juridica_tres);
        btnCadastrar = (Button) findViewById(R.id.btn_cadastro);
        edtEmail = (EditText)findViewById(R.id.ed_email_consumidor);
        edtTelefone = (EditText)findViewById(R.id.ed_telefones);
        edtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                strEmail = s.toString();
                if (TextUtils.isEmpty(strEmail)) {
                    edtEmail.setError("Campo Obrigatório");
                    Validacoes.requestFocus(edtEmail);
                    haEmail = true;

                } else if (!Validacoes.validaEmail(strEmail)) {
                    edtEmail.setError("E-mail digitado incorretamente");
                    Validacoes.requestFocus(edtEmail);
                    haEmail = true;
                }

                //Toast.makeText(CadastroPessoaJuridica.this,haNf+"'"+haRs+"'"+haIe+"'"+haIm+"'"+haCnpj+"'",Toast.LENGTH_SHORT).show();
            }
        });
        edtTelefone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                strTelefone = s.toString();
                if (TextUtils.isEmpty(strTelefone)) {
                    edtTelefone.setError("Campo Obrigatório");
                    Validacoes.requestFocus(edtTelefone);
                    haTelefone = true;
                }

                //Toast.makeText(CadastroPessoaJuridica.this,haNf+"'"+haRs+"'"+haIe+"'"+haIm+"'"+haCnpj+"'",Toast.LENGTH_SHORT).show();
            }
        });
        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifica();
            }
        });

    }
    public void verifica(){

        haEmail = false;
        haTelefone = false;
        edtEmail.setError(null);
        edtTelefone.setError(null);

        if (TextUtils.isEmpty(edtEmail.getText().toString())) {
            edtEmail.setError("Campo Obrigatório");
            Validacoes.requestFocus(edtEmail);
            haEmail = true;
        }
        if (TextUtils.isEmpty(edtTelefone.getText().toString())) {
            edtTelefone.setError("Campo Obrigatório");
            Validacoes.requestFocus(edtTelefone);
            haTelefone = true;
        }
        if(haEmail!= true && haTelefone != true){

            AlertDialog.Builder builder = new AlertDialog.Builder(CadastroPessoaJuridicaTres.this);
            builder.setTitle("Conta registrada");
            builder.setMessage("CadastroPessoaFisica realizado, confirme o acesso via e-mail");
            builder.setPositiveButton("Fechar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(new Intent(CadastroPessoaJuridicaTres.this, LoginPessoaJuridica.class));
                    finish();
                }
            });
            builder.setCancelable(false);
            builder.show();

        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, CadastroPessoaJuridicaDois.class));
        finishActivity(0);
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
