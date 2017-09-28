package com.example.talit.projetotcc.activities;

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
import android.widget.Spinner;

import com.example.talit.projetotcc.R;
import com.example.talit.projetotcc.Validacoes.Validacoes;
import com.example.talit.projetotcc.mascaras.MascaraCep;
import com.example.talit.projetotcc.mascaras.MascaraCnpJ;

public class CadastroPessoaJuridicaDois extends AppCompatActivity {

    private Button btnContinuar;
    private TextWatcher twCep;
    private EditText edtRua;
    private EditText edtNum;
    private EditText edtComple;
    private EditText edtBairro;
    private EditText edtCidade;
    private EditText edtEstado;
    private EditText edtCep;
    private Spinner spnUfs;
    private String strRua;
    private String strNum;
    private String strComple;
    private String strBairro;
    private String strCidade;
    private String strEstado;
    private String strCep;
    private String strUf;
    private boolean haRua;
    private boolean haNum;
    private boolean haComple;
    private boolean haBairro;
    private boolean haCidade;
    private boolean haEstado;
    private boolean haCep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_cadastro_pessoa_juridica_dois);

        btnContinuar = (Button) findViewById(R.id.btn_proximo);
        edtRua = (EditText) findViewById(R.id.ed_rua);
        edtNum = (EditText) findViewById(R.id.ed_numero);
        edtComple = (EditText) findViewById(R.id.ed_complemento);
        edtBairro = (EditText) findViewById(R.id.ed_bairro);
        edtCidade = (EditText) findViewById(R.id.ed_cidade);
        edtEstado = (EditText) findViewById(R.id.ed_estado);
        edtCep = (EditText) findViewById(R.id.ed_cep);
        spnUfs = (Spinner)findViewById(R.id.sp_tp_ufs);
        twCep = MascaraCep.insert("######-###", edtCep);
        edtCep.addTextChangedListener(twCep);
        edtRua.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                strRua = s.toString();
                if (TextUtils.isEmpty(strRua)) {
                    edtRua.setError("Campo Obrigatório");
                    Validacoes.requestFocus(edtRua);
                    haRua = true;

                } else if (strRua.length() > 150) {
                    edtRua.setError("Nome muito grande");
                    Validacoes.requestFocus(edtRua);
                    haRua = true;
                }

                //Toast.makeText(CadastroPessoaJuridica.this,haNf+"'"+haRs+"'"+haIe+"'"+haIm+"'"+haCnpj+"'",Toast.LENGTH_SHORT).show();
            }
        });
        edtNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                strNum = s.toString();
                if (TextUtils.isEmpty(strNum)) {
                    edtNum.setError("Campo Obrigatório");
                    Validacoes.requestFocus(edtNum);
                    haNum = true;

                } else if (strNum.length() > 150) {
                    edtNum.setError("Nome muito grande");
                    Validacoes.requestFocus(edtNum);
                    haNum = true;
                }

                //Toast.makeText(CadastroPessoaJuridica.this,haNf+"'"+haRs+"'"+haIe+"'"+haIm+"'"+haCnpj+"'",Toast.LENGTH_SHORT).show();
            }
        });
        edtComple.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                strComple = s.toString();
                if (TextUtils.isEmpty(strComple)) {
                    edtComple.setError("Campo Obrigatório");
                    Validacoes.requestFocus(edtComple);
                    haComple = true;

                } else if (strComple.length() > 150) {
                    edtComple.setError("Nome muito grande");
                    Validacoes.requestFocus(edtComple);
                    haComple = true;
                }

                //Toast.makeText(CadastroPessoaJuridica.this,haNf+"'"+haRs+"'"+haIe+"'"+haIm+"'"+haCnpj+"'",Toast.LENGTH_SHORT).show();
            }
        });
        edtBairro.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                strBairro = s.toString();
                if (TextUtils.isEmpty(strBairro)) {
                    edtBairro.setError("Campo Obrigatório");
                    Validacoes.requestFocus(edtBairro);
                    haBairro = true;

                } else if (strBairro.length() > 150) {
                    edtBairro.setError("Nome muito grande");
                    Validacoes.requestFocus(edtBairro);
                    haBairro = true;
                }

                //Toast.makeText(CadastroPessoaJuridica.this,haNf+"'"+haRs+"'"+haIe+"'"+haIm+"'"+haCnpj+"'",Toast.LENGTH_SHORT).show();
            }
        });
        edtCidade.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                strCidade = s.toString();
                if (TextUtils.isEmpty(strCidade)) {
                    edtCidade.setError("Campo Obrigatório");
                    Validacoes.requestFocus(edtCidade);
                    haCidade = true;

                } else if (strCidade.length() > 150) {
                    edtCidade.setError("Nome muito grande");
                    Validacoes.requestFocus(edtCidade);
                    haCidade = true;
                }

                //Toast.makeText(CadastroPessoaJuridica.this,haNf+"'"+haRs+"'"+haIe+"'"+haIm+"'"+haCnpj+"'",Toast.LENGTH_SHORT).show();
            }
        });
        edtEstado.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                strEstado = s.toString();
                if (TextUtils.isEmpty(strEstado)) {
                    edtEstado.setError("Campo Obrigatório");
                    Validacoes.requestFocus(edtEstado);
                    haEstado = true;

                } else if (strEstado.length() > 150) {
                    edtEstado.setError("Nome muito grande");
                    Validacoes.requestFocus(edtEstado);
                    haEstado = true;
                }

                //Toast.makeText(CadastroPessoaJuridica.this,haNf+"'"+haRs+"'"+haIe+"'"+haIm+"'"+haCnpj+"'",Toast.LENGTH_SHORT).show();
            }
        });
        edtCep.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                strCep = s.toString();
                if (TextUtils.isEmpty(strCep)) {
                    edtCep.setError("Campo Obrigatório");
                    Validacoes.requestFocus(edtCep);
                    haCep = true;

                } else if (strCep.length() > 10) {
                    edtCep.setError("Nome muito grande");
                    Validacoes.requestFocus(edtCep);
                    haCep = true;
                }

                //Toast.makeText(CadastroPessoaJuridica.this,haNf+"'"+haRs+"'"+haIe+"'"+haIm+"'"+haCnpj+"'",Toast.LENGTH_SHORT).show();
            }
        });
        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               verifica();
            }
        });
    }
    public void verifica(){

        haRua = false;
        haNum = false;
        haComple = false;
        haBairro = false;
        haCidade = false;
        haEstado = false;
        haCep = false;
        edtRua.setError(null);
        edtNum.setError(null);
        edtComple.setError(null);
        edtBairro.setError(null);
        edtCidade.setError(null);
        edtEstado.setError(null);
        edtCep.setError(null);

        if (TextUtils.isEmpty(edtRua.getText().toString())){
            edtRua.setError("Campo Obrigatório");
            Validacoes.requestFocus(edtRua);
            haRua = true;
        }
        if (TextUtils.isEmpty(edtNum.getText().toString())){
            edtNum.setError("Campo Obrigatório");
            Validacoes.requestFocus(edtNum);
            haNum = true;
        }
        if (TextUtils.isEmpty(edtComple.getText().toString())){
            edtComple.setError("Campo Obrigatório");
            Validacoes.requestFocus(edtComple);
            haComple = true;
        }
        if (TextUtils.isEmpty(edtBairro.getText().toString())){
            edtBairro.setError("Campo Obrigatório");
            Validacoes.requestFocus(edtBairro);
            haBairro = true;
        }
        if (TextUtils.isEmpty(edtCidade.getText().toString())){
            edtCidade.setError("Campo Obrigatório");
            Validacoes.requestFocus(edtCidade);
            haCidade = true;
        }
        if (TextUtils.isEmpty(edtEstado.getText().toString())) {
            edtEstado.setError("Campo Obrigatório");
            Validacoes.requestFocus(edtEstado);
            haEstado = true;
        }
        if (TextUtils.isEmpty(edtCep.getText().toString())) {
            edtCep.setError("Campo Obrigatório");
            Validacoes.requestFocus(edtCep);
            haCep = true;
        }
        if(haRua!=true && haNum!= true && haComple!= true && haBairro != true && haCidade!=true && haEstado!= true && haCep != true){
            startActivity(new Intent(this, CadastroPessoaJuridicaTres.class));
            finish();
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, CadastroPessoaJuridica.class));
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
