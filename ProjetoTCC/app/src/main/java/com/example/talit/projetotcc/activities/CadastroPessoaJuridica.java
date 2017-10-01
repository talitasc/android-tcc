package com.example.talit.projetotcc.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.talit.projetotcc.R;
import com.example.talit.projetotcc.Validacoes.Validacoes;
import com.example.talit.projetotcc.mascaras.MascaraCnpJ;
import com.example.talit.projetotcc.mascaras.MascaraCpf;
import com.example.talit.projetotcc.mascaras.MascaraTelefone;

public class CadastroPessoaJuridica extends AppCompatActivity {

    private Button btnContinuar;
    private TextWatcher twCpnj;
    private EditText edtNomeFantasia;
    private EditText edtRazaoSocial;
    private EditText edtTelefone;
    private EditText edtCnpj;
    private String strCnpj;
    private boolean haNf;
    private boolean haRs;
    private boolean haIe;
    private boolean haIm;
    private boolean haCnpj;
    private String telStr;
    private String[] tpTel;
    private String esTel;
    private TextWatcher twTel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_cadastro_pessoa_juridica);
        btnContinuar = (Button) findViewById(R.id.btn_proximo);
        edtNomeFantasia = (EditText) findViewById(R.id.edt_nome_fantasia);
        edtRazaoSocial = (EditText) findViewById(R.id.ed_razao_social);
        edtTelefone = (EditText) findViewById(R.id.ed_telefones);
        edtCnpj = (EditText) findViewById(R.id.ed_cnpj);

        btnContinuar.setVisibility(View.INVISIBLE);

        twCpnj = MascaraCnpJ.insert("##.###.###/####-##", edtCnpj);
        edtCnpj.addTextChangedListener(twCpnj);
        twTel = MascaraTelefone.insert("(##)####-####", edtTelefone);
        edtTelefone.addTextChangedListener(twTel);
        edtNomeFantasia.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (TextUtils.isEmpty(s.toString().trim())) {
                    edtNomeFantasia.setError("Campo Obrigatório");
                    Validacoes.requestFocus(edtNomeFantasia);
                    haNf = true;

                } else if (s.length() > 150) {
                    edtNomeFantasia.setError("Nome muito grande");
                    Validacoes.requestFocus(edtNomeFantasia);
                    haNf = true;
                }
                habilitaBotao();
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

                if (TextUtils.isEmpty(s.toString().trim())) {
                    edtRazaoSocial.setError("Campo Obrigatório");
                    Validacoes.requestFocus(edtRazaoSocial);
                    haRs = true;

                } else if (s.length() > 150) {
                    edtRazaoSocial.setError("Campo Obrigatório");
                    Validacoes.requestFocus(edtRazaoSocial);
                    haRs = true;
                }

                habilitaBotao();
            }
        });
        edtTelefone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                habilitaBotao();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(edtTelefone.getText().toString())) {
                    edtTelefone.setError("Campo Obrigatório");
                    Validacoes.requestFocus(edtTelefone);
                    haIe = true;


                } else if (edtTelefone.getText().toString().length()!= 13) {
                    edtTelefone.setError("Telefone inválido");
                    Validacoes.requestFocus(edtTelefone);
                    haIe = true;

                }else{
                    haIe=false;
                    edtTelefone.setError(null);
                }

                habilitaBotao();

            }

            @Override
            public void afterTextChanged(Editable s) {
                habilitaBotao();

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
                strCnpj = edtCnpj.getText().toString();
                if (TextUtils.isEmpty(strCnpj)) {
                    edtCnpj.setError("Campo Obrigatório");
                    Validacoes.requestFocus(edtCnpj);
                    haCnpj = true;

                }else if(strCnpj.length() != 18){
                    edtCnpj.setError("CNPJ inválido");
                    Validacoes.requestFocus(edtCnpj);
                    haCnpj = true;

                } else {
                    haCnpj = false;
                    edtCnpj.setError(null);
                    habilitaBotao();

                }

            }
        });
        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String telefoneCompleto = edtTelefone.getText().toString().replace("(","").replace(")","").replace("-","");
                String dd = telefoneCompleto.substring(0,2);
                String telefone = telefoneCompleto.substring(2,telefoneCompleto.length());
                String cpf = edtCnpj.getText().toString().replace(".","").replace("/","").replace("-","");
                Intent intent = new Intent();
                intent.setClass(CadastroPessoaJuridica.this,CadastroPessoaJuridicaDois.class);
                intent.putExtra("CNPJ",cpf);
                intent.putExtra("RAZAO_SOCIAL",edtRazaoSocial.getText().toString());
                intent.putExtra("NOME_FANTASIA",edtNomeFantasia.getText().toString());
                intent.putExtra("DD",dd);
                intent.putExtra("TELEFONE",telefone);
                CadastroPessoaJuridica.this.startActivity(intent);
                CadastroPessoaJuridica.this.finish();
            }
        });
    }

    public void habilitaBotao(){

        if(!TextUtils.isEmpty(edtRazaoSocial.getText().toString()) &&
                !TextUtils.isEmpty(edtNomeFantasia.getText().toString())&& !TextUtils.isEmpty(edtCnpj.getText().toString())&&
                !TextUtils.isEmpty(edtTelefone.getText().toString())){
            btnContinuar.setVisibility(View.VISIBLE);
           }else{
            btnContinuar.setVisibility(View.INVISIBLE);
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
