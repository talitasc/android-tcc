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
import android.widget.Toast;

import com.example.talit.projetotcc.R;
import com.example.talit.projetotcc.Validacoes.Validacoes;
import com.example.talit.projetotcc.mascaras.MascaraCnpJ;

public class CadastroPessoaJuridica extends AppCompatActivity {

    private Button btnContinuar;
    private TextWatcher twCpnj;
    private EditText edtNomeFantasia;
    private EditText edtRazaoSocial;
    private EditText edtIe;
    private EditText edtIm;
    private EditText edtCnpj;
    private String strNomeFantasia;
    private String strRazaoSocial;
    private String strIe;
    private String strIm;
    private String strCnpj;
    private boolean haNf;
    private boolean haRs;
    private boolean haIe;
    private boolean haIm;
    private boolean haCnpj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_cadastro_pessoa_juridica);
        btnContinuar = (Button) findViewById(R.id.btn_proximo);
        edtNomeFantasia = (EditText) findViewById(R.id.edt_nome_fantasia);
        edtRazaoSocial = (EditText) findViewById(R.id.ed_razao_social);
        edtIe = (EditText) findViewById(R.id.ed_incricao_estadual);
        edtIm = (EditText) findViewById(R.id.ed_incricao_municipal);
        edtCnpj = (EditText) findViewById(R.id.ed_cnpj);


        //twCpnj = MascaraCnpJ.insert("##.###.###/####-##", edtCnpj);
        //edtCnpj.addTextChangedListener(twCpnj);
        edtNomeFantasia.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                strNomeFantasia = s.toString();
                if (TextUtils.isEmpty(strNomeFantasia)) {
                    edtNomeFantasia.setError("Campo Obrigatório");
                    Validacoes.requestFocus(edtNomeFantasia);
                    haNf = true;

                } else if (strNomeFantasia.length() > 150) {
                    edtNomeFantasia.setError("Nome muito grande");
                    Validacoes.requestFocus(edtNomeFantasia);
                    haNf = true;
                }

                //Toast.makeText(CadastroPessoaJuridica.this,haNf+"'"+haRs+"'"+haIe+"'"+haIm+"'"+haCnpj+"'",Toast.LENGTH_SHORT).show();
            }
        });
        edtRazaoSocial.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                strRazaoSocial = toString();
                if (TextUtils.isEmpty(strRazaoSocial)) {
                    edtRazaoSocial.setError("Campo Obrigatório");
                    Validacoes.requestFocus(edtRazaoSocial);
                    haRs = true;

                } else if (strRazaoSocial.length() > 150) {
                    edtRazaoSocial.setError("Campo Obrigatório");
                    Validacoes.requestFocus(edtRazaoSocial);
                    haRs = true;
                }
            }
        });
        edtIe.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                strIe = toString();
                if (TextUtils.isEmpty(strIe)) {
                    edtIe.setError("Campo Obrigatório");
                    Validacoes.requestFocus(edtIe);
                    haIe = true;

                } else if (strIe.length() > 150) {
                    edtIe.setError("Campo Obrigatório");
                    Validacoes.requestFocus(edtIe);
                    haIe = true;
                }
            }
        });
        edtIm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                strIm = toString();

                if (TextUtils.isEmpty(strIm)) {
                    edtIm.setError("Campo Obrigatório");
                    Validacoes.requestFocus(edtIm);
                    haIm = true;

                } else if (strIm.length() > 150) {
                    edtIm.setError("Campo Obrigatório");
                    Validacoes.requestFocus(edtIm);
                    haIm = true;
                }
            }
        });
        edtCnpj.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                strCnpj = s.toString();
                if (TextUtils.isEmpty(strCnpj)) {
                    edtCnpj.setError("Campo Obrigatório");
                    Validacoes.requestFocus(edtCnpj);
                    haCnpj = true;
                }else if(!Validacoes.isNumeric(strCnpj)){
                    Toast.makeText(CadastroPessoaJuridica.this,strCnpj,Toast.LENGTH_SHORT).show();
                    edtCnpj.setError("CNPJ incorreto");
                    Validacoes.requestFocus(edtCnpj);
                    haCnpj = true;
                }
            }
        });
        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifica();
            }
        });


    }

    public void verifica() {

        haIm = false;
        haIe = false;
        haRs = false;
        haCnpj = false;
        haNf = false;
        edtNomeFantasia.setError(null);
        edtRazaoSocial.setError(null);
        edtIe.setError(null);
        edtIm.setError(null);
        edtCnpj.setError(null);

        if (TextUtils.isEmpty(edtRazaoSocial.getText().toString())) {
            edtRazaoSocial.setError("Campo Obrigatório");
            Validacoes.requestFocus(edtRazaoSocial);
            haRs = true;
        }
        if (TextUtils.isEmpty(edtNomeFantasia.getText().toString())) {
            edtNomeFantasia.setError("Campo Obrigatório");
            Validacoes.requestFocus(edtNomeFantasia);
            haNf = true;
        }
        if (TextUtils.isEmpty(edtCnpj.getText().toString())) {
            edtCnpj.setError("Campo Obrigatório");
            Validacoes.requestFocus(edtCnpj);
            haCnpj = true;
        }
        if (TextUtils.isEmpty(edtIe.getText().toString())) {
            edtIe.setError("Campo Obrigatório");
            Validacoes.requestFocus(edtIe);
            haIe = true;
        }
        if (TextUtils.isEmpty(edtIm.getText().toString())) {
            edtIm.setError("Campo Obrigatório");
            Validacoes.requestFocus(edtIm);
            haIm = true;
        }
        if (haIe != true && haIm != true && haCnpj != true && haNf != true && haRs != true) {

            startActivity(new Intent(this, CadastroPessoaJuridicaDois.class));
            finish();

        }

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, RedirecionaPessoaJuridica.class));
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
